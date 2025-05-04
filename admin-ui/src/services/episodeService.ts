import axiosInstance from "../axiosConfig";
import { AxiosResponse } from "axios";

export interface Episode {
  id: number;
  title: string;
  episodeNumber: number;
  synopsis?: string;
  duration?: number;
  thumbnail?: {
    url: string;
  };
  videoUrl?: string;
  viewCount: number;
  airDate?: string;
  state: 'released' | 'upcoming';
  status: number;
  createdAt: string;
  updatedAt: string;
  filmId: string;
}


const getEpisodesByFilmId = (filmId: string): Promise<AxiosResponse<Episode[]>> => {
  return axiosInstance.get(`/v1/film/${filmId}/episodes`);
};

const getEpisodeById = (id: number): Promise<AxiosResponse<Episode>> => {
  return axiosInstance.get(`/v1/episode/${id}`);
};


export const episodeService = {
  getEpisodesByFilmId,
  getEpisodeById,
};

export default episodeService;