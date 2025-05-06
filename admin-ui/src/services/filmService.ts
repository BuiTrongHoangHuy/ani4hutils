import axiosInstance from "../axiosConfig";
import { AxiosResponse } from "axios";

export interface Film {
  id: string;
  title: string;
  startDate: string;
  endDate: string;
  synopsis: string;
  images: Array<{
    url: string;
  }>;
  avgStar: number;
  totalStar: number;
  maxEpisodes: number;
  numEpisodes: number;
  year: number;
  season: string;
  averageEpisodeDuration: number;
  ageRatingId: string;
  status: number;
  state: string;
  ageRating: {
    id: string;
    shortName: string;
    longName: string;
    description: string;
  };
  genres: Array<{
    id: string;
    name: string;
  }>;
  createdAt: string;
  updatedAt: string;
  seriesId?: string;
}

export interface FilmFilter {
  title?: string;
  genreId?: string;
  ageRatingId?: string;
  year?: number;
  state?: string;
}

export interface Paging {
  page: number;
  size: number;
}

export interface FilmListResponse {
  data: Film[];
}

export interface FilmCreate {
  title: string;
  startDate?: string;
  endDate?: string;
  synopsis?: string;
  images?: Array<{url:string}>;
  maxEpisodes?: number;
  numEpisodes?: number;
  year: number;
  season?: string;
  averageEpisodeDuration?: number;
  ageRatingId: string;
  state: string;
  genreIds: string[];
  seriesId?: number;
}

export interface FilmUpdate {
  id: string;
  title?: string;
  startDate?: string;
  endDate?: string;
  synopsis?: string;
  images?: Array<{
    url: string;
  }>;
  maxEpisodes?: number;
  numEpisodes?: number;
  year?: number;
  season?: string;
  averageEpisodeDuration?: number;
  ageRatingId?: string;
  state?: string;
  genreIds?: string[];
  seriesId?: number;
}
export interface FilmResponse {
 data: Film;
}

const getFilms = (paging: Paging, filter?: FilmFilter): Promise<AxiosResponse<FilmListResponse>> => {
  const params = { ...paging, ...filter };
  console.log(params);
  return axiosInstance.get("/v1/film");
};

const getFilmById = (id: string): Promise<AxiosResponse<FilmResponse>> => {
  return axiosInstance.get(`/v1/film/${id}`);
};

const createFilm = (film: FilmCreate): Promise<AxiosResponse<Film>> => {
  return axiosInstance.post("/v1/film", film);
};

const updateFilm = (film: FilmUpdate): Promise<AxiosResponse<Film>> => {
  return axiosInstance.put(`/v1/film/${film.id}`, film);
};

const deleteFilm = (id: string): Promise<AxiosResponse<void>> => {
  return axiosInstance.delete(`/v1/film/${id}`);
};

export const filmService = {
  getFilms,
  getFilmById,
  createFilm,
  updateFilm,
  deleteFilm,
};

export default filmService;