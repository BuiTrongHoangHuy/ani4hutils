export interface PagingSearch{
    cursor?: string | null,
    nextCursor?: string,
    page: number,
    pageSize: number,
}