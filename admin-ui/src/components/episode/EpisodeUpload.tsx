import React, { useState } from 'react';
import { EpisodeCreate } from '../../services/episodeService';

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
    state: 'upcoming',
    filmId: filmId
  });

  const [videoFile, setVideoFile] = useState<File | null>(null);

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
        
        <div className="form-control mb-6">
          <label className="label">
            <span className="label-text">Video File</span>
          </label>
          <input
            type="file"
            accept="video/*"
            onChange={handleFileChange}
            className="file-input file-input-bordered w-full"
            required
          />
          <label className="label">
            <span className="label-text-alt">Supported formats: MP4, WebM, etc.</span>
          </label>
        </div>

        
        <div className="flex justify-end space-x-2">
          <button
            type="button"
            onClick={onCancel}
            className="btn btn-outline"
          >
            Cancel
          </button>
          <button
            type="submit"
            className="btn btn-primary"
          >
            Upload
          </button>
        </div>
      </form>
    </div>
  );
};

export default EpisodeUpload;