import {SearchList} from "@/types/search/searchList";
import FavoriteCard from "@/components/FavoriteCard";

export default function FavoritePage() {
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