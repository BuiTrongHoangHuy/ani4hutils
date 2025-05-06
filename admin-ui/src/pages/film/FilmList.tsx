import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import filmService, { Film, FilmFilter, Paging } from '../../services/filmService';

function FilmList () {
  const [films, setFilms] = useState<Film[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [totalPages, setTotalPages] = useState<number>(0);

  const [paging, setPaging] = useState<Paging>({
    page: 0,
    size: 10
  });

  const [filter, setFilter] = useState<FilmFilter>({
    title: '',
    year: undefined,
    state: undefined
  });

  useEffect(() => {
    const fetchFilms = async () => {
      setLoading(true);
      try {
        const response = await filmService.getFilms(paging, filter);
        setFilms(response.data.data);
        //setTotalPages(response.data.totalPages);
        setError(null);
      } catch (err) {
        setError('Failed to fetch films. Please try again later.');
        console.error('Error fetching films:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchFilms();
  }, [paging.page, paging.size, filter]);

  const handleFilterChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFilter(prev => ({
      ...prev,
      [name]: value === '' ? undefined : name === 'year' ? parseInt(value) : value
    }));
    setPaging(prev => ({ ...prev, page: 0 }));
  };

  const handlePageChange = (newPage: number) => {
    setPaging(prev => ({ ...prev, page: newPage }));
  };



  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <div>
          <h1 className="text-2xl font-bold">Film Management</h1>
          {!loading && <p className="text-gray-600">Total films: 100</p>}
        </div>
        <Link to="/film/create" className="btn btn-primary">
          Add New Film
        </Link>
      </div>

      <div className="bg-base-200 p-4 rounded-lg mb-6">
        <h2 className="text-lg font-semibold mb-3">Filter Films</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label className="label">Title</label>
            <input
              type="text"
              name="title"
              value={filter.title || ''}
              onChange={handleFilterChange}
              className="input input-bordered w-full"
              placeholder="Search by title"
            />
          </div>
          <div>
            <label className="label">Year</label>
            <input
              type="number"
              name="year"
              value={filter.year || ''}
              onChange={handleFilterChange}
              className="input input-bordered w-full"
              placeholder="Filter by year"
            />
          </div>
          <div>
            <label className="label">State</label>
            <select
              name="state"
              value={filter.state || ''}
              onChange={handleFilterChange}
              className="select select-bordered w-full"
            >
              <option value="">All States</option>
              <option value="FINISHED">FINISHED</option>
              <option value="UPCOMING">UPCOMING</option>
              <option value="ON_AIR">ON AIR</option>
            </select>
          </div>
        </div>
      </div>

      {error && (
        <div className="alert alert-error mb-4">
          <span>{error}</span>
        </div>
      )}

      {loading ? (
        <div className="flex justify-center my-8">
          <div className="loading loading-spinner loading-lg"></div>
        </div>
      ) : (
          <div className="overflow-x-auto">
            <table className="table w-full">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Thumbnail</th>
                  <th>Title</th>
                  <th>Year</th>
                  <th>Episodes</th>
                  <th>AVG Star</th>
                  <th>State</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {films.length > 0 ? (
                  films.map((film) => (
                    <tr key={film.id}>
                      <td>{film.id}</td>
                      <td>
                        {film.images ? (
                          <img
                            src={film.images[0].url}
                            alt={film.title}
                            className="w-16 h-auto rounded"
                          />
                        ) : (
                          <div className="w-16 h-24 bg-gray-200 rounded flex items-center justify-center">
                            No Image
                          </div>
                        )}
                      </td>
                      <td>{film.title}</td>
                      <td>{film.year}</td>
                      <td>
                        {film.numEpisodes}/{film.maxEpisodes}
                      </td>
                      <td>{film.avgStar || 'N/A'}</td>
                      <td>
                        <span className={`badge ${
                          film.state === 'FINISHED' ? 'badge-success' :
                          film.state === 'UPCOMING' ? 'badge-warning' :
                          film.state === 'ON_AIR' ? 'badge-warning' :
                          'badge-error'
                        }`}>
                          {film.state}
                        </span>
                      </td>
                      <td>
                        <div className="flex space-x-2">
                          <Link
                            to={``}
                            className="btn btn-sm btn-info"
                          >
                            View
                          </Link>
                          <Link
                            to={``}
                            className="btn btn-sm btn-warning"
                          >
                            Edit
                          </Link>
                          <button
                            className="btn btn-sm btn-error"
                          >
                            Delete
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={8} className="text-center py-4">
                      No films found
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>

      )}
    </div>
  );
};

export default FilmList;
