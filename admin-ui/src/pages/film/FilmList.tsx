import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import filmService, { Film, FilmFilter, Paging } from '../../services/filmService';

function FilmList () {
  const [films, setFilms] = useState<Film[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [inputTitle, setInputTitle] = useState<string>('');
  const [inputYear, setInputYear] = useState<string>('');

  const [paging, setPaging] = useState<Paging>({
    page: 1,
    size: 10
  });

  const [filter, setFilter] = useState<FilmFilter>({
  });

  useEffect(() => {
    const fetchFilms = async () => {
      setLoading(true);
      try {
        const response = await filmService.getFilms(paging, filter);
        setFilms(response.data.data.data);
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
    setPaging(prev => ({ ...prev, page: 1 }));
  };



  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <div>
          <h1 className="text-2xl font-bold">Film Management</h1>
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
              value={inputTitle || ''}
              onChange={(e) => setInputTitle(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  setFilter((prev) => ({ ...prev, title: inputTitle || undefined }));
                  setPaging((prev) => ({ ...prev, page: 1 }));
                }
              }}
              className="input input-bordered w-full"
              placeholder="Search by title"
            />
          </div>
          <div>
            <label className="label">Year</label>
            <input
              type="number"
              name="year"
              value={inputYear || ''}
              onChange={(e) => setInputYear(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  setFilter((prev) => ({ ...prev, year: inputYear || undefined }));
                  setPaging((prev) => ({ ...prev, page: 1 }));
                }
              }}
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
              <option value="finished">FINISHED</option>
              <option value="upcoming">UPCOMING</option>
              <option value="on_air">ON AIR</option>
            </select>
          </div>
        </div>
      </div>

      {error && (
        <div role="alert" className="alert alert-error mb-4">
          <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6 shrink-0 stroke-current" fill="none" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <span>{error}</span>
        </div>
      )}

      {loading ? (
        <div className="flex justify-center my-8">
          <div className="loading loading-spinner loading-lg"></div>
        </div>
      ) : (
        <>
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
                            src={film.images[0]?.url||"https://placehold.co/300x400/png?text=ani4h.site"}
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
                            to={`/film/${film.id}`}
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

        </>
      )}
    </div>
  );
};

export default FilmList;
