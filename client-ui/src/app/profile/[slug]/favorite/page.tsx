import {SearchList} from "@/types/search/searchList";
import FavoriteCard from "@/components/FavoriteCard";

export default function FavoritePage() {
    const data: SearchList[] = [

    ]


    return (
        <div className={"flex flex-col w-full space-y-8 ml-8"}>
            <p className={"font-bold text-xl text-orange-500"}>Favorite</p>
            <div className={"grid grid-cols-2 gap-8"}>
                {data.map((film, i) =>
                    <FavoriteCard film={film} key={i} />
                )}
            </div>
        </div>
    )
}