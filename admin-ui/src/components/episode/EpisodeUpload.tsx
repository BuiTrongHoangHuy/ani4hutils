import React, { useState } from 'react';
import episodeService, { EpisodeCreate } from '../../services/episodeService';

interface EpisodeUploadProps {
  filmId: string;
  nextEpisodeNumber: number;
  onEpisodeAdded: () => void;
  onCancel: () => void;
}

const EpisodeUpload: React.FC<EpisodeUploadProps> = ({
  filmId,
  nextEpisodeNumber,
  onEpisodeAdded,
  onCancel
}) => {
  const [formData, setFormData] = useState<EpisodeCreate>({
    title: `Episode ${nextEpisodeNumber}`,
    episodeNumber: nextEpisodeNumber,
    synopsis: '',
    duration: 0,
    state: 'UPCOMING',
    filmId: filmId
  });

  const [videoFile, setVideoFile] = useState<File | null>(null);
  const [uploading, setUploading] = useState<boolean>(false);
  const [uploadProgress, setUploadProgress] = useState<number>(0);
  const [error, setError] = useState<string | null>(null);
  const [videoUrlInput, setVideoUrlInput] = useState('');
  const [useDirectLink, setUseDirectLink] = useState(false);
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'duration' ? parseInt(value) || 0 : value
    }));
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setVideoFile(e.target.files[0]);
    }
  };

  const uploadVideoToS3 = async (url: string, file: File): Promise<void> => {
    return new Promise((resolve, reject) => {
      const xhr = new XMLHttpRequest();

      xhr.upload.addEventListener('progress', (event) => {
        if (event.lengthComputable) {
          const progress = Math.round((event.loaded / event.total) * 100);
          setUploadProgress(progress);
        }
      });

      xhr.addEventListener('load', () => {
        if (xhr.status >= 200 && xhr.status < 300) {
          resolve();
        } else {
          reject(new Error(`Upload failed with status ${xhr.status}`));
        }
      });

      xhr.addEventListener('error', () => {
        reject(new Error('Upload failed due to network error'));
      });

      xhr.open('PUT', url);
      xhr.setRequestHeader('Content-Type', file.type);
      xhr.send(file);
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!useDirectLink && !videoFile) {
      setError('Please select a video file to upload or use a direct link');
      return;
    }

    if (useDirectLink && !videoUrlInput.trim()) {
      setError('Please provide a valid video URL.');
      return;
    }
    setUploading(true);
    setError(null);

    try {
      let videoUrl: string;
      if (useDirectLink) {
        videoUrl = videoUrlInput.trim();
      } else {
        const fileExtension = videoFile!.name.split('.').pop() || '';
        const uploadUrlResponse = await episodeService.getVideoUploadUrl(
            filmId,
            formData.episodeNumber,
            fileExtension
        );

        const uploadUrl = uploadUrlResponse.data.data;

        await uploadVideoToS3(uploadUrl, videoFile!);
        videoUrl = uploadUrl.split('?')[0];
      }
      console.log("ádasdasdkjhasdkashkj",videoUrl);
      await episodeService.createEpisode({
        ...formData,
        videoUrl
      });

      onEpisodeAdded();
    } catch (err) {
      console.error('Error uploading episode:', err);
      setError('Failed to upload episode. Please try again later.');
      setUploading(false);
    } finally {
      setUploading(false);
    }
  };

  return (
    <div>
      {error && (
        <div className="alert alert-error mb-4">
          <span>{error}</span>
        </div>
      )}

      <form onSubmit={handleSubmit}>
        <div className="grid grid-cols-2 gap-4 mb-4">
          <div className="form-control">
            <label className="label">
              <span className="label-text">Episode Number</span>
            </label>
            <input
              type="number"
              name="episodeNumber"
              value={formData.episodeNumber}
              onChange={handleInputChange}
              className="input input-bordered"
              required
              min="1"
            />
          </div>

          <div className="form-control">
            <label className="label">
              <span className="label-text">Title</span>
            </label>
            <input
              type="text"
              name="title"
              value={formData.title}
              onChange={handleInputChange}
              className="input input-bordered"
              required
            />
          </div>
        </div>

        <div className="form-control mb-4 grid grid-cols-1 w-full">
          <label className="label">
            <span className="label-text">Synopsis</span>
          </label>
          <textarea
            name="synopsis"
            value={formData.synopsis || ''}
            onChange={handleInputChange}
            className="textarea textarea-bordered h-24 w-full"
          />
        </div>

        <div className="grid grid-cols-2 gap-4 mb-4">
          <div className="form-control">
            <label className="label">
              <span className="label-text">Duration (minutes)</span>
            </label>
            <input
              type="number"
              name="duration"
              value={formData.duration || ''}
              onChange={handleInputChange}
              className="input input-bordered"
              min="0"
            />
          </div>

          <div className="form-control">
            <label className="label">
              <span className="label-text">State</span>
            </label>
            <select
              name="state"
              value={formData.state}
              onChange={handleInputChange}
              className="select select-bordered"
              required
            >
              <option value="upcoming">Upcoming</option>
              <option value="released">Released</option>
            </select>
          </div>
        </div>

        <div className="form-control mb-4">
          <label className="label cursor-pointer">
            <span className="label-text">Use direct video URL</span>
            <input
                type="checkbox"
                className="toggle toggle-primary"
                checked={useDirectLink}
                onChange={() => setUseDirectLink(!useDirectLink)}
            />
          </label>
        </div>

        {useDirectLink ? (
            <div className="form-control mb-6">
              <label className="label">
                <span className="label-text">Video URL</span>
              </label>
              <input
                  type="url"
                  value={videoUrlInput}
                  onChange={(e) => setVideoUrlInput(e.target.value)}
                  className="input input-bordered"
                  placeholder="https://example.com/video.mp4"
                  required
              />
            </div>
        ) : (
            <div className="form-control mb-6">
              <label className="label">
                <span className="label-text">Video File</span>
              </label>
              <input
                  type="file"
                  accept="video/*"
                  onChange={handleFileChange}
                  className="file-input file-input-bordered w-full"
                  required={!useDirectLink}
              />
              <label className="label">
                <span className="label-text-alt">Supported formats: MP4, WebM, etc.</span>
              </label>
            </div>
        )}

        {uploading && !useDirectLink && (
          <div className="mb-4">
            <progress
              className="progress progress-primary w-full"
              value={uploadProgress}
              max="100"
            ></progress>
            <p className="text-center mt-1">{uploadProgress}% Uploaded</p>
          </div>
        )}

        <div className="flex justify-end space-x-2">
          <button
            type="button"
            onClick={onCancel}
            className="btn btn-outline"
            disabled={uploading}
          >
            Cancel
          </button>
          <button
            type="submit"
            className="btn btn-primary"
            disabled={uploading}
          >
            {uploading ? 'Uploading...' : 'Upload Episode'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default EpisodeUpload;