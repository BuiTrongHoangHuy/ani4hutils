export interface Paging {
    cursor: string;
    nextCursor: string;
    page: number;
    pageSize: number;
    [key: string]: string | number | undefined;

}