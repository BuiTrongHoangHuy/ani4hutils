import Image from "next/image";
import { X} from "lucide-react";
interface film {
    images :{url:string,width: number,height: number}[],
    title:string,
    genres:string[],
    synopsis:string
}
export default function HistoryCard(
    {film}: {film: film}
){
    return (
        <div
            className={"flex flex-row w-full cursor-pointer hover:scale-103 duration-200 space-x-4 rounded-md"}
            style={{background: "#FFFFFF1A"}}>
            <div className=" w-56 h-38 relative">
                <Image
                    className="rounded-md object-cover"
                    fill={true}
                    src={film.images?.[0]?.url || "https://placehold.co/200x300/png?text=ani4h"}
                    alt={film.title}
                />
                <div className="absolute bottom-0 left-0 w-full bg-white h-2 rounded-b-md overflow-hidden">
                    <div className="bg-orange-500 h-full" style={{ width: '50%' }}></div>
                </div>
            </div>
            <div className={"flex flex-col flex-1 mt-2 space-y-4"}>
                <div className={"flex flex-row justify-between"}>
                    <p className="font-bold line-clamp-1">{film.title}</p>
                    <X className={"text-white mr-4"} strokeWidth={3}/>
                </div>
                <p className="font-normal">Genres: {film.genres.join(", ")}</p>
                <p className="font-normal opacity-50 line-clamp-2">Views: {film.synopsis}</p>
            </div>
        </div>
    )
}