import axiosInstance, {axiosInstance2} from "../axiosConfig";
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
  state: 'RELEASED' | 'UPCOMING';
  status: number;
  createdAt: string;
  updatedAt: string;
  filmId: string;
}

export interface EpisodeCreate {
  title: string;
  episodeNumber: number;
  synopsis?: string;
  duration?: number;
  thumbnail?: string;
  videoUrl?: string;
  airDate?: string;
  state: 'RELEASED' | 'UPCOMING';
  filmId: string;
}

export interface EpisodeUpdate {
  title?: string;
  episodeNumber?: number;
  synopsis?: string;
  duration?: number;
  thumbnail?: string;
  videoUrl?: string;
  airDate?: string;
  state?: 'RELEASED' | 'UPCOMING';
}
export interface EpisodeListResponse {
  data: Episode[];
}
const getEpisodesByFilmId = (filmId: string): Promise<AxiosResponse<EpisodeListResponse>> => {
  return axiosInstance.get(`/v1/film/${filmId}/episodes`);
};

const getEpisodeById = (id: number): Promise<AxiosResponse<Episode>> => {
  return axiosInstance.get(`/v1/episode/${id}`);
};

const createEpisode = (episode: EpisodeCreate): Promise<AxiosResponse<Episode>> => {
  return axiosInstance.post(`/v1/episode`, episode);
};

const updateEpisode = (id: number,episode: EpisodeUpdate): Promise<AxiosResponse<Episode>> => {
  return axiosInstance.put(`/v1/episode/${id}`, episode);
};



const getVideoUploadUrl = (filmId: string, episodeNumber: number, fileExtension: string): Promise<AxiosResponse<{data: string}>> => {
  return axiosInstance2.get(`/v1/upload/episode-video-url`, {
    params: {
      filmId,
      episodeNumber,
      fileExtension
    }
  });
};

export const episodeService = {
  getEpisodesByFilmId,
  getEpisodeById,
  createEpisode,
  updateEpisode,
  getVideoUploadUrl
};

export default episodeService;