
export interface Episode {
    id: string,
    title: string,
    episodeNumber: number,
    synopsis?: string,
    duration?: number,
    thumbnail?: string,
    videoUrl: string,
    viewCount: number,
    airDate: string,
    state: string,
    status: number,
    createdAt: Date,
    updatedAt: Date,
    filmId: string
}