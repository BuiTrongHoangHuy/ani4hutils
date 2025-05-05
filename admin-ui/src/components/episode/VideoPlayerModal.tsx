import React from 'react';
import ReactPlayer from 'react-player';

interface VideoPlayerModalProps {
  videoUrl: string;
  onClose: () => void;
}

const VideoPlayerModal: React.FC<VideoPlayerModalProps> = ({ videoUrl, onClose }) => {
  return (
    <div className="fixed inset-0 bg-black bg-opacity-75 flex items-center justify-center z-50">
      <div className="bg-base-100 p-4 rounded-lg w-full max-w-4xl">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold">Video Player</h2>
          <button 
            onClick={onClose}
            className="btn btn-sm btn-circle"
          >
            ✕
          </button>
        </div>
        
        <div className="relative h-108 w-full">
          <ReactPlayer
            url={videoUrl}
            width="100%"
            height="100%"
            controls
            playing
            className="absolute top-0 left-0"
          />
        </div>
        
        <div className="mt-4 flex justify-end">
          <button 
            onClick={onClose}
            className="btn btn-primary"
          >
            Close
          </button>
        </div>
      </div>
    </div>
  );
};

export default VideoPlayerModal;