import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import filmService, { Film } from '../../services/filmService';
import EpisodeList from '../../components/episode/EpisodeList';

const FilmDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [film, setFilm] = useState<Film | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchFilm = async () => {
    if (!id) return;

    setLoading(true);
    try {
      const response = await filmService.getFilmById(id);
      setFilm(response.data.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch film details. Please try again later.');
      console.error('Error fetching film details:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFilm();
  }, [id]);


  if (loading) {
    return (
      <div className="flex justify-center items-center h-64">
        <div className="loading loading-spinner loading-lg"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container mx-auto p-4">
        <div className="alert alert-error">
          <span>{error}</span>
        </div>
        <div className="mt-4">
          <Link to="/film" className="btn btn-primary">
            Back to Films
          </Link>
        </div>
      </div>
    );
  }

  if (!film) {
    return (
      <div className="container mx-auto p-4">
        <div className="alert alert-warning">
          <span>Film not found</span>
        </div>
        <div className="mt-4">
          <Link to="/film" className="btn btn-primary">
            Back to Films
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">{film.title}</h1>
        <div className="flex space-x-2">
          <Link to="/film" className="btn btn-outline">
            Back to Films
          </Link>
          <Link to={``} className="btn btn-warning">
            Edit
          </Link>
          <button className="btn btn-error">
            Delete
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="md:col-span-1">
          {film.images ? (
            <img
              src={film.images[0].url}
              alt={film.title}
              className="w-full h-auto rounded-lg shadow-lg"
            />
          ) : (
            <div className="w-full h-96 bg-gray-200 rounded-lg flex items-center justify-center">
              No Poster Available
            </div>
          )}
        </div>

        <div className="md:col-span-2">
          <div className="bg-base-200 p-6 rounded-lg shadow">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <h2 className="text-lg font-semibold mb-4">Basic Information</h2>
                <div className="space-y-2">
                  <p><span className="font-medium">ID:</span> {film.id}</p>
                  <p><span className="font-medium">Year:</span> {film.year}</p>
                  <p><span className="font-medium">Age Rating:</span> {film.ageRating?.shortName || 'N/A'}</p>
                  <p>
                    <span className="font-medium">State:</span>{' '}
                    <span className={`badge ${
                      film.state === 'ACTIVE' ? 'badge-success' :
                      film.state === 'INACTIVE' ? 'badge-warning' :
                      'badge-error'
                    }`}>
                      {film.state}
                    </span>
                  </p>
                  <p><span className="font-medium">Episodes:</span> {film.numEpisodes}/{film.maxEpisodes}</p>
                  <p><span className="font-medium">Average Episode Duration:</span> {film.averageEpisodeDuration} minutes</p>
                  <p><span className="font-medium">Season:</span> {film.season || 'N/A'}</p>
                </div>
              </div>

              <div>
                <h2 className="text-lg font-semibold mb-4">Ratings & Dates</h2>
                <div className="space-y-2">
                  <p><span className="font-medium">Average Rating:</span> {film.avgStar ? `${film.avgStar.toFixed(1)}/5` : 'No ratings'}</p>
                  <p><span className="font-medium">Total Ratings:</span> {film.totalStar || 0}</p>
                  <p><span className="font-medium">Start Date:</span> {film.startDate ? new Date(film.startDate).toLocaleDateString() : 'N/A'}</p>
                  <p><span className="font-medium">End Date:</span> {film.endDate ? new Date(film.endDate).toLocaleDateString() : 'N/A'}</p>
                  <p><span className="font-medium">Created:</span> {new Date(film.createdAt).toLocaleString()}</p>
                  <p><span className="font-medium">Last Updated:</span> {new Date(film.updatedAt).toLocaleString()}</p>
                </div>
              </div>
            </div>

            <div className="mt-6">
              <h2 className="text-lg font-semibold mb-2">Synopsis</h2>
              <p className="bg-base-100 p-4 rounded">{film.synopsis || 'No synopsis available.'}</p>
            </div>

            <div className="mt-6">
              <h2 className="text-lg font-semibold mb-2">Genres</h2>
              <div className="flex flex-wrap gap-2">
                {film.genres && film.genres.length > 0 ? (
                  film.genres.map((genre) => (
                    <span key={genre.id} className="badge badge-primary">
                      {genre.name}
                    </span>
                  ))
                ) : (
                  <p>No genres specified</p>
                )}
              </div>
            </div>

            {film.seriesId && (
              <div className="mt-6">
                <h2 className="text-lg font-semibold mb-2">Series Information</h2>
                <p>
                  <span className="font-medium">Part of Series ID:</span> {film.seriesId}
                  <Link to={`/film/${film.seriesId}`} className="btn btn-xs btn-link ml-2">
                    View Series
                  </Link>
                </p>
              </div>
            )}
          </div>
        </div>
      </div>

      {film && (
        <EpisodeList
          filmId={film.id}
          maxEpisodes={film.maxEpisodes}
          numEpisodes={film.numEpisodes}
          onEpisodeAdded={fetchFilm}
        />
      )}
    </div>
  );
};

export default FilmDetail;
