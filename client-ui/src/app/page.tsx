import Carousel from "@/app/carousel";
import FilmListCard from "@/components/FilmListCard";
import {FilmList} from "@/types/filmList";
import ListFilm from "@/components/ListFilm";


const film : FilmList =
{
    id: "51321",
    title: "Solo leveling: Cằm nhọn siêu cấp ",
    state: "finished",
    image:
    {
        "url": "https://cdn.myanimelist.net/images/anime/1257/121634.jpg",
        "width": 0,
        "height": 0,
    },
    series: {
      id: "1",
      name: ""
    },
    maxEpisode: 1,
    numEpisode: 1,
}

const filmList = [film,film,film,film,film,film]
export default function Home() {
  return (
      <div className={"w-screen"}>
          <Carousel className="w-full h-[80vh] z-[30]" />
          <div className="px-10 -mt-12 relative z-[40] space-y-4">
              <ListFilm title={"Có thể bạn sẽ thích"} films={filmList}/>
              <ListFilm title={"Có thể bạn sẽ thích"} films={filmList}/>
              <ListFilm title={"Có thể bạn sẽ thích"} films={filmList}/>
              <ListFilm title={"Có thể bạn sẽ thích"} films={filmList}/>
              <ListFilm title={"Có thể bạn sẽ thích"} films={filmList}/>
          </div>
      </div>
  );
}
