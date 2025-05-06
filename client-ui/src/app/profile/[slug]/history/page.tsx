import HistoryCard from "@/components/HistoryCard";

export default function HistoryPage() {
    const data = [
        {
            id: "1",
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
            title: "EP01: Solo Leveling",
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
        <div>
            <div className={"flex flex-col w-full space-y-8 ml-8"}>
                <p className={"font-bold text-xl text-orange-500"}>History</p>
                <div className={"flex flex-col gap-8"}>
                    {data.map((film, i) =>
                        <HistoryCard film={film} key={i} />
                    )}
                </div>
            </div>
        </div>
    )
}