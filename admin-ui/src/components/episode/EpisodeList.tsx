import React, { useState, useEffect } from 'react';
import episodeService, { Episode } from '../../services/episodeService';
import EpisodeUpload from './EpisodeUpload';
import EpisodeEdit from './EpisodeEdit';
import VideoPlayerModal from './VideoPlayerModal';

interface EpisodeListProps {
  filmId: string;
  maxEpisodes: number;
  numEpisodes: number;
  onEpisodeAdded: () => void;
}

const EpisodeList: React.FC<EpisodeListProps> = ({ 
  filmId, 
  maxEpisodes, 
  numEpisodes,
  onEpisodeAdded 
}) => {
  const [episodes, setEpisodes] = useState<Episode[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [showUploadModal, setShowUploadModal] = useState<boolean>(false);
  const [showEditModal, setShowEditModal] = useState<boolean>(false);
  const [currentEpisode, setCurrentEpisode] = useState<Episode | null>(null);
  const [showVideoModal, setShowVideoModal] = useState<boolean>(false);
  const [currentVideoUrl, setCurrentVideoUrl] = useState<string>('');

  useEffect(() => {
    fetchEpisodes();
  }, [filmId]);

  const fetchEpisodes = async () => {
    setLoading(true);
    try {
      const response = await episodeService.getEpisodesByFilmId(filmId);
      setEpisodes(response.data.data);
      setError(null);
    } catch (err) {
      console.error('Error fetching episodes:', err);
      setError('Failed to load episodes. Please try again later.');
    } finally {
      setLoading(false);
    }
  };



  const handleEpisodeAdded = () => {
    fetchEpisodes();
    onEpisodeAdded();
    setShowUploadModal(false);
  };

  const handleEpisodeUpdated = () => {
    fetchEpisodes();
    onEpisodeAdded();
    setShowEditModal(false);
    setCurrentEpisode(null);
  };

  const handleEditEpisode = (episode: Episode) => {
    setCurrentEpisode(episode);
    setShowEditModal(true);
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center h-24">
        <div className="loading loading-spinner loading-md"></div>
      </div>
    );
  }

  return (
    <div className="mt-8">
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-semibold">Episodes ({numEpisodes}/{maxEpisodes})</h2>
        <button 
          className="btn btn-primary btn-sm"
          onClick={() => setShowUploadModal(true)}
          disabled={numEpisodes >= maxEpisodes}
        >
          Add Episode
        </button>
      </div>

      {error && (
        <div className="alert alert-error mb-4">
          <span>{error}</span>
        </div>
      )}

      {episodes.length === 0 ? (
        <div className="bg-base-200 p-6 rounded-lg text-center">
          No episodes available for this film.
        </div>
      ) : (
        <div className="overflow-x-auto">
          <table className="table w-full">
            <thead>
              <tr>
                <th>Episode #</th>
                <th>Title</th>
                <th>Duration</th>
                <th>Status</th>
                <th>Video</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {episodes.map((episode) => (
                <tr key={episode.id}>
                  <td>{episode.episodeNumber}</td>
                  <td>{episode.title}</td>
                  <td>{episode.duration ? `${episode.duration} min` : 'N/A'}</td>
                  <td>
                    <span className={`badge ${
                      episode.state === 'RELEASED' ? 'badge-success' : 'badge-warning'
                    }`}>
                      {episode.state}
                    </span>
                  </td>
                  <td>
                    {episode.videoUrl ? (
                      <button
                        onClick={() => {
                          setCurrentVideoUrl(episode.videoUrl || '');
                          setShowVideoModal(true);
                        }}
                        className="btn btn-xs btn-outline"
                      >
                        View
                      </button>
                    ) : (
                      <span className="text-error">No video</span>
                    )}
                  </td>
                  <td>
                    <div className="flex space-x-2">
                      <button
                        onClick={() => handleEditEpisode(episode)}
                        className="btn btn-xs btn-warning"
                      >
                        Edit
                      </button>
                      <button
                        className="btn btn-xs btn-error"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showUploadModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-base-100 p-6 rounded-lg w-full max-w-2xl">
            <h2 className="text-xl font-semibold mb-4">Add New Episode</h2>
            <EpisodeUpload
              filmId={filmId}
              nextEpisodeNumber={numEpisodes + 1}
              onEpisodeAdded={handleEpisodeAdded}
              onCancel={() => setShowUploadModal(false)}
            />
          </div>
        </div>
      )}

      {showEditModal && currentEpisode && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-base-100 p-6 rounded-lg w-full max-w-2xl">
            <h2 className="text-xl font-semibold mb-4">Edit Episode</h2>
            <EpisodeEdit
              episode={currentEpisode}
              onEpisodeUpdated={handleEpisodeUpdated}
              onCancel={() => {
                setShowEditModal(false);
                setCurrentEpisode(null);
              }}
            />
          </div>
        </div>
      )}

      {showVideoModal && currentVideoUrl && (
        <VideoPlayerModal
          videoUrl={currentVideoUrl}
          onClose={() => setShowVideoModal(false)}
        />
      )}
    </div>
  );
};

export default EpisodeList;
