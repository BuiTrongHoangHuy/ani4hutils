import Carousel from "@/app/carousel";
import ListFilm from "@/components/ListFilm";
import {FilmList} from "@/types/filmList";


export default async function Home() {
    const data = await fetch("http://localhost:4000/v1/film?ageRatingId=gGzTFTGJB8Wj&limit=1")
    const films : FilmList[] = (await data.json()).data || []
    console.log(films)
    return (
        <div className={"w-screen"}>
            <Carousel className="w-full h-[80vh] z-[30]"/>
            <div className="px-10 -mt-12 relative z-[40] w-screen">
                <ListFilm title={"Có thể bạn sẽ thích"} films={films}/>
                <ListFilm title={"Có thể bạn sẽ thích"} films={films}/>
                <ListFilm title={"Có thể bạn sẽ thích"} films={films}/>
                <ListFilm title={"Có thể bạn sẽ thích"} films={films}/>
                <ListFilm title={"Có thể bạn sẽ thích"} films={films}/>
            </div>
        </div>
    );
}
