'use client'
import {SearchList} from "@/types/searchList";
import SearchListCard from "@/components/SearchListCard";
import FilmCard from "@/components/FilmCard";
import {useEffect} from "react";
import {useSearchParams} from "next/navigation";

export default function SearchPage() {
    const data: SearchList[] = [
        {
            id: "1",
            title: "Solo Leveling",
            images: [
                {
                    url: "https://cdn.myanimelist.net/images/anime/1448/147351.jpg",
                    width: 200,
                    height: 300
                }
            ],
            genres: ["Action", "Adventure"],
            synopsis: "Sung Jin-Woo, dubbed the weakest hunter of all mankind, grows stronger by the day with the supernatural powers he has gained. However, keeping his skills hidden becomes more difficult as dungeon-related incidents pile up around him.\\n\\nWhen Jin-Woo and a few other low-ranked hunters are the only survivors of a dungeon that turns out to be a bigger challenge than initially expected, he draws attention once again, and hunter guilds take an increased interest in him. Meanwhile, a strange hunter who has been lost for ten years returns with a dire warning about an upcoming catastrophic event.",
            year: 2002,
            views: 1000000,
            avgStar: 4.5,
            maxEpisodes: 23,
            numEpisodes: 24
        },
        {
            id: "2",
            title: "Solo Leveling",
            images: [
                {
                    url: "https://cdn.myanimelist.net/images/anime/1448/147351.jpg",
                    width: 200,
                    height: 300
                }
            ],
            genres: ["Action", "Adventure"],
            synopsis: "Sung Jin-Woo, dubbed the weakest hunter of all mankind, grows stronger by the day with the supernatural powers he has gained. However, keeping his skills hidden becomes more difficult as dungeon-related incidents pile up around him.\\n\\nWhen Jin-Woo and a few other low-ranked hunters are the only survivors of a dungeon that turns out to be a bigger challenge than initially expected, he draws attention once again, and hunter guilds take an increased interest in him. Meanwhile, a strange hunter who has been lost for ten years returns with a dire warning about an upcoming catastrophic event.",
            year: 1999,
            views: 2000000,
            avgStar: 4.8,
            maxEpisodes: 23,
            numEpisodes: 24
        },
        {
            id: "3",
            title: "Solo Leveling",
            images: [
                {
                    url: "https://cdn.myanimelist.net/images/anime/1448/147351.jpg",
                    width: 200,
                    height: 300
                }
            ],
            genres: ["Action", "Adventure"],
            synopsis: "Sung Jin-Woo, dubbed the weakest hunter of all mankind, grows stronger by the day with the supernatural powers he has gained. However, keeping his skills hidden becomes more difficult as dungeon-related incidents pile up around him.\\n\\nWhen Jin-Woo and a few other low-ranked hunters are the only survivors of a dungeon that turns out to be a bigger challenge than initially expected, he draws attention once again, and hunter guilds take an increased interest in him. Meanwhile, a strange hunter who has been lost for ten years returns with a dire warning about an upcoming catastrophic event.",
            year: 1999,
            views: 2000000,
            avgStar: 4.8,
            maxEpisodes: 23,
            numEpisodes: 24
        },
        {
            id: "4",
            title: "Solo Leveling",
            images: [
                {
                    url: "https://cdn.myanimelist.net/images/anime/1448/147351.jpg",
                    width: 200,
                    height: 300
                }
            ],
            genres: ["Action", "Adventure"],
            synopsis: "Sung Jin-Woo, dubbed the weakest hunter of all mankind, grows stronger by the day with the supernatural powers he has gained. However, keeping his skills hidden becomes more difficult as dungeon-related incidents pile up around him.\\n\\nWhen Jin-Woo and a few other low-ranked hunters are the only survivors of a dungeon that turns out to be a bigger challenge than initially expected, he draws attention once again, and hunter guilds take an increased interest in him. Meanwhile, a strange hunter who has been lost for ten years returns with a dire warning about an upcoming catastrophic event.",
            year: 1999,
            views: 2000000,
            avgStar: 4.8,
            maxEpisodes: 23,
            numEpisodes: 24
        },
        {
            id: "5",
            title: "Solo Leveling",
            images: [
                {
                    url: "https://cdn.myanimelist.net/images/anime/1448/147351.jpg",
                    width: 200,
                    height: 300
                }
            ],
            genres: ["Action", "Adventure"],
            synopsis: "Sung Jin-Woo, dubbed the weakest hunter of all mankind, grows stronger by the day with the supernatural powers he has gained. However, keeping his skills hidden becomes more difficult as dungeon-related incidents pile up around him.\\n\\nWhen Jin-Woo and a few other low-ranked hunters are the only survivors of a dungeon that turns out to be a bigger challenge than initially expected, he draws attention once again, and hunter guilds take an increased interest in him. Meanwhile, a strange hunter who has been lost for ten years returns with a dire warning about an upcoming catastrophic event.",
            year: 1999,
            views: 2000000,
            avgStar: 4.8,
            maxEpisodes: 23,
            numEpisodes: 24
        },
    ]
    const searchParams = useSearchParams();
    const query = searchParams.get("q") || "";
    useEffect(() => {
        console.log("hellooooooooo");
    }, [])

    return (
        <div className={"w-screen mt-[64px] pl-20 pr-20 pt-8"}>
            <div className={"flex flex-row w-full h-full space-x-4"}>
                <div className={"flex flex-col flex-1 h-full space-y-8"}>
                    {
                        data.map((film, index) =>
                            <SearchListCard film={film} key={index} />
                        )
                    }
                </div>

                <div className={"flex flex-col w-86 h-full space-y-4"}>
                    <div className={"flex flex-col space-y-4"}>
                        <p className={"text-xl font-bold"}>Proposal for you</p>
                        <div className={"flex flex-row overflow-x-auto h-45 space-x-2"}>
                            {
                                data.map((film, index) =>
                                    <FilmCard key={index} film={film} height={32} width={24} fontSize={14}/>
                                )
                            }
                        </div>
                    </div>
                    <div className={"flex flex-col space-y-4"}>
                        <p className={"text-xl font-bold"}>Top search</p>
                        {data.map((film, index) => {
                            let rankColor = "text-gray-400"; // default
                            if (index === 0) rankColor = "text-orange-500";
                            else if (index === 1) rankColor = "text-orange-400";
                            else if (index === 2) rankColor = "text-orange-300";

                            return (
                                <div key={index} className="flex flex-row items-center space-x-2">
                                    <p className={`text-xl font-bold ${rankColor}`}>{index + 1}</p>
                                    <FilmCard film={film} row={true} height={20} width={16} fontSize={14} />
                                </div>
                            );
                        })}
                    </div>
                </div>
            </div>
        </div>
    );
}