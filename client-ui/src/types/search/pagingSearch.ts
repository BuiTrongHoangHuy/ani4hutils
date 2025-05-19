export interface PagingSearch{
    cursor?: string | null,
    nextCursor?: string,
    page: number,
    pageSize: number,
    [key: string]: string | number | undefined | null;
}