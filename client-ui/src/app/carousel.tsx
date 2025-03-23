'use client'
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination } from "swiper/modules";
import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';
import 'swiper/css/scrollbar';
import Link from "next/link";

export default function Carousel({ className }: { className: string }) {
    return (
        <Swiper
            className={`${className}`}
            modules={[Navigation, Pagination]}
            onSlideChange={() => console.log('slide change')}
            onSwiper={(swiper) => console.log(swiper)}
            navigation
            pagination={{ clickable: true,
                bulletClass: 'swiper-pagination-bullet mx-1 w-2 h-2 bg-gray-400 rounded-full transition-all duration-300',
                // Class cho bullet active - dài hơn
                bulletActiveClass: 'swiper-pagination-bullet-active w-6 h-2 bg-blue-600 rounded-full'}}
            slidesPerView={1}
            loop={true}
        >
            <SwiperSlide>
                <div className="flex flex-col h-full w-full bg-[url('/love-is-war.jpg')] bg-cover bg-center box-border shadow-[inset_0_0_150px_100px_#000000]">
                    <div className={"mt-auto p-20 "}>
                        <Link href={"https://animevietsub.lol/phim/cuoc-chien-to-tinh-a3328/"}
                              className={"text-2xl font-bold drop-shadow-2xl mb-20"}>Kaguya love is war</Link>
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