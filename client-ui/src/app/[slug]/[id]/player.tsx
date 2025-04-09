'use client'

import ReactPlayer from "react-player";
import {ChangeEvent, useRef, useState} from "react";
import {OnProgressProps} from "react-player/types/base";
import {ExpandIcon, PauseIcon, PlayIcon, ShrinkIcon, Volume2, VolumeX} from "lucide-react";
import {formatTime} from "@/utils/format";

export default function Player() {
    const player = useRef<ReactPlayer>(null);
    const playerWrapper = useRef<HTMLDivElement  | null>(null);
    const [levels, setLevels] = useState([]);
    const [playing, setPlaying] = useState(false);
    const [progress, setProgress] = useState(0);
    const [duration, setDuration] = useState(0);
    const [isFullscreen, setIsFullscreen] = useState(false);
    const [muted, setMuted] = useState(false)
    const [playedTime, setPlayedTime] = useState(0);
    const handleReady = () => {
        const hls = player.current?.getInternalPlayer("hls");
        if (!player.current) return
        if (hls && hls.levels) {
            setLevels(hls.levels);
        }
        //setDuration(player.current.getDuration());
    };
    const onChangeBitrate = (e: ChangeEvent<HTMLSelectElement>) => {
        const selectedLevel = e.target.value;
        const hls = player.current?.getInternalPlayer("hls");
        if (hls) {
            hls.currentLevel = selectedLevel;
        }
    };
    const togglePlay = () => {
        setPlaying(!playing);
    };

    const handleProgress = (state:  OnProgressProps) => {
        setProgress(state.played);
        setPlayedTime(state.playedSeconds)
        console.log(state.played)
    };
    const toggleFullscreen = () => {
        if (!isFullscreen) {
            playerWrapper.current?.requestFullscreen(); // Vào fullscreen
            setIsFullscreen(true);
        } else {
            document.exitFullscreen().then();
            setIsFullscreen(false);
        }
    };

    const handleSeek = (e : ChangeEvent<HTMLInputElement>) => {
        const seekValue = parseFloat(e.target.value);
        setProgress(seekValue);
        player.current?.seekTo(seekValue);
    };

    const toggleMute = () =>{
        setMuted(!muted);
    }

    const handleDuration = (duration:number) =>{
        setDuration(duration)
    }
    return (
        <div className="player-wrapper" ref={playerWrapper}>
            <ReactPlayer
                ref={player}
                url="https://d2oh79ptmlqizl.cloudfront.net/output.webm/master.m3u8"
                playing={playing}
                muted={muted}
                controls={false}
                config={{
                    file: {
                        forceHLS: true,
                    },
                }}
                onDuration={handleDuration}
                onReady={handleReady}
                onProgress={handleProgress}
                width="100%"
                height="100%"
            />
            <div className="absolute bottom-0 left-0 right-0 px-4 pb-2 pt-4 flex items-center text-white
            gap-3 justify-between w-full bg-base-100 opacity-50">
                <button onClick={togglePlay}>{playing ? <PauseIcon size={18}/> : <PlayIcon size={18}/>}</button>
                <input type="range" min="0" step={"any"} max="1" onChange={handleSeek} value={progress}
                       className="absolute top-0 left-o right-0 h-1 range w-full range-xs range-primary"/>
                <button  onClick={toggleMute}>
                    {muted ? <VolumeX size={18}/> : <Volume2 size={18}/> }
                </button>
                <span>{formatTime(playedTime)} / {formatTime(duration)}</span>
                <select
                onChange={onChangeBitrate}>
                {levels.length > 0 ? (
                    levels.map((level: { height: number }, id) => (
                        <option key={id} value={id}>
                            {level.height}p
                        </option>
                    ))
                ) : (
                    <option>Loading...</option>
                )}
            </select>
                <button onClick={toggleFullscreen}>
                    {isFullscreen ? <ShrinkIcon size={18}/> :<ExpandIcon size={18}/>}
                </button>
            </div>
        </div>
    );
};
