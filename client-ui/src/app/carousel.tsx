'use client'
import { Swiper, SwiperSlide } from "swiper/react";
import {Autoplay, Navigation, Pagination} from "swiper/modules";
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import Link from "next/link";

export default function Carousel({ className }: { className: string }) {
    return (
        <Swiper
            className={`${className}`}
            modules={[Navigation, Pagination,Autoplay]}
            onSlideChange={() => console.log('slide change')}
            onSwiper={(swiper) => console.log(swiper)}
            pagination={{ clickable: true,
                horizontalClass: "carousel-horizontal-pagination",
                bulletElement: "div",
                bulletClass: 'w-4 rounded-4xl mx-2 h-4 bg-gray-400 opacity-50 inline-block',
                bulletActiveClass: 'w-8'}}
            slidesPerView={1}
            navigation={{
            }}
            autoplay={
            {
                delay: 3000
            }
            }
            loop={true}
        >
            <SwiperSlide>
                <div className="flex  flex-col h-full w-full bg-[url('/love-is-war.jpg')] bg-cover bg-center box-border shadow-[inset_0_0_150px_100px_#000000]">
                    <div className={"mt-auto p-20 "}>
                        <div  className={"text-2xl font-bold drop-shadow-2xl mb-10"}>
                            <Link href={"https://animevietsub.lol/phim/cuoc-chien-to-tinh-a3328/"}
                            >Kaguya love is war</Link>
                        </div>
                        <div className={"flex gap-4"}>
                            <div className={"btn btn-primary rounded-xs"}>vip</div>
                            <div className={"btn opacity-60 rounded-xs text-white"}>Rom-com</div>
                            <div className={"btn opacity-60 rounded-xs  text-white"}>School</div>
                        </div>
                    </div>
                </div>
            </SwiperSlide>
            <SwiperSlide>
                <div className=" w-full h-[200px]"></div>
            </SwiperSlide>
            <SwiperSlide>
                <div className=" w-full h-[200px]"></div>
            </SwiperSlide>
            <SwiperSlide>
                <div className=" w-full h-[200px]"></div>
            </SwiperSlide>
        </Swiper>
    );
}