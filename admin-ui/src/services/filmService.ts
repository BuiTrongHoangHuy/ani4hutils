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

const getFilms = (paging: Paging, filter?: FilmFilter): Promise<AxiosResponse<FilmListResponse>> => {
  const params = { ...paging, ...filter };
  return axiosInstance.get("/v1/film");
};

const getFilmById = (id: string): Promise<AxiosResponse<Film>> => {
  return axiosInstance.get(`/v1/film/${id}`);
};



export const filmService = {
  getFilms,
  getFilmById,

};

export default filmService;