import Carousel from "@/app/carousel";
import ListFilm from "@/components/ListFilm";
import {FilmList} from "@/types/filmList";
import {SearchService} from "@/app/search/service";
import {PagingSearch} from "@/types/search/pagingSearch";
import {Paging} from "@/types/paging"
import { cookies } from 'next/headers'


export default async function Home() {
    const paging: PagingSearch = {
        cursor: "",
        page: 1,
        pageSize: 25,
    }

    const pagingNormal: Paging = {
        cursor: "",
        nextCursor: "",
        page: 1,
        pageSize: 25,
    }
    const cookieStore = await cookies()
    const userId = cookieStore.get('userId')?.value || ""

    const resFavorite = await SearchService.userFavoriteRecommendation(userId,0, paging)
    const favorites : FilmList[] = (await resFavorite.data.data || [])

    const resHistory = await SearchService.userHistoryRecommendation(userId,0, paging)
    const histories : FilmList[] = (await resHistory.data.data || [])

    const resTopHot = await SearchService.getTopHot(pagingNormal);
    const topHot: FilmList[] = resTopHot?.data ?? [];


    return (
        <div className={"w-screen"}>
            <Carousel className="w-full h-[80vh] z-[30]"/>
            <div className="px-10 -mt-12 relative z-[40] w-screen space-y-15">
                <ListFilm title={"Top Hot"} films={topHot}/>
                <ListFilm title={"Proposal for you"} films={favorites}/>
                <ListFilm title={"You might like"} films={histories}/>
            </div>
        </div>
    );
}
