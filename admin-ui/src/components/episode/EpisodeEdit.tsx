import React, { useState } from 'react';
import { Episode, EpisodeUpdate } from '../../services/episodeService';

interface EpisodeEditProps {
  episode: Episode;
  onEpisodeUpdated: () => void;
  onCancel: () => void;
}

const EpisodeEdit: React.FC<EpisodeEditProps> = ({
  episode,
  onEpisodeUpdated,
  onCancel
}) => {
  const [formData, setFormData] = useState<EpisodeUpdate>({
    id: episode.id,
    title: episode.title,
    episodeNumber: episode.episodeNumber,
    synopsis: episode.synopsis || '',
    duration: episode.duration || 0,
    state: episode.state,
    videoUrl: episode.videoUrl
  });

  const [videoFile, setVideoFile] = useState<File | null>(null);
  const [uploading, setUploading] = useState<boolean>(false);
  const [uploadProgress, setUploadProgress] = useState<number>(0);
  const [error, setError] = useState<string | null>(null);

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


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

  };

  return (
    <div>
      {error && (
        <div className="alert alert-error mb-4">
          <span>{error}</span>
        </div>
      )}
      
      <form onSubmit={handleSubmit}>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
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
        
        <div className="form-control mb-4">
          <label className="label">
            <span className="label-text">Synopsis</span>
          </label>
          <textarea
            name="synopsis"
            value={formData.synopsis || ''}
            onChange={handleInputChange}
            className="textarea textarea-bordered h-24"
          />
        </div>
        
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
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
              <option value="UPCOMING">Upcoming</option>
              <option value="RELEASED">Released</option>
            </select>
          </div>
        </div>
        
        <div className="form-control mb-6">
          <label className="label">
            <span className="label-text">Video File (Optional)</span>
          </label>
          <input
            type="file"
            accept="video/*"
            onChange={handleFileChange}
            className="file-input file-input-bordered w-full"
          />
          <label className="label">
            <span className="label-text-alt">Leave empty to keep the current video</span>
          </label>
          {episode.videoUrl && (
            <div className="mt-2">
              <span className="text-sm">Current video: </span>
              <a 
                href={episode.videoUrl} 
                target="_blank" 
                rel="noopener noreferrer"
                className="link link-primary text-sm"
              >
                View
              </a>
            </div>
          )}
        </div>
        
        {uploading && videoFile && (
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
            {uploading ? 'Updating...' : 'Update Episode'}
          </button>
        </div>
      </form>
    </div>
  );
};

export default EpisodeEdit;