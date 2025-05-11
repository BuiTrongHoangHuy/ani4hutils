'use client'
import {useEffect, useState} from "react";
import {CircleCheck, CirclePlus} from "lucide-react";
import {FavoriteService} from "@/app/profile/[slug]/favorite/service";

interface ButtonFavoriteProps {
    userId: string;
    filmId: string;
}

export default function ButtonFavorite(
    props: ButtonFavoriteProps
) {
    const [isFavorite, setIsFavorite] = useState(false);
    useEffect(() => {
        const checkFavorite = async () => {
            const res = await FavoriteService.checkFavorite(props.userId, props.filmId);
            setIsFavorite(res.data);
        }
        checkFavorite();
    }, []);

    const handleClick = async () => {
        if(isFavorite) {
            setIsFavorite(false);
            await FavoriteService.removeFavorite(props.userId, props.filmId);
        }
        else {
            setIsFavorite(true);
            await FavoriteService.addFavorite(props.userId, props.filmId);
        }
    }

    return (
        <div className={`btn ${isFavorite?"bg-orange-600":"bg-gray-500"} hover:gray-300 text-2xl px-8 py-6`}
            onClick={handleClick}
        >
            { isFavorite ?
                <CircleCheck className={"text-white"} strokeWidth={3} size={20}/>
                : <CirclePlus className={"text-white"} strokeWidth={3} size={20}/>
            }
            <p>{isFavorite?"Saved":"Save"}</p>
        </div>
    )
}