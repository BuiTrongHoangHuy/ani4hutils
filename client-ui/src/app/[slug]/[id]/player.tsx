'use client'

import ReactPlayer from "react-player";
import {ChangeEvent, useRef, useState} from "react";
import {OnProgressProps} from "react-player/types/base";
import {ExpandIcon, PauseIcon, PlayIcon, ShrinkIcon} from "lucide-react";

export default function Player() {
    const player = useRef<ReactPlayer>(null);
    const playerWrapper = useRef<HTMLDivElement  | null>(null);
    const [levels, setLevels] = useState([]);
    const [playing, setPlaying] = useState(false);
    const [progress, setProgress] = useState(0);
    const [, setDuration] = useState(0);
    const [isFullscreen, setIsFullscreen] = useState(false);
    const handleReady = () => {
        const hls = player.current?.getInternalPlayer("hls");
        if (!player.current) return
        if (hls && hls.levels) {
            setLevels(hls.levels);
        }
        setDuration(player.current.getDuration());
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

    return (
        <div className="player-wrapper" ref={playerWrapper}>
            <ReactPlayer
                ref={player}
                url="https://d2oh79ptmlqizl.cloudfront.net/output.webm/master.m3u8"
                playing={playing}
                controls={false}
                config={{
                    file: {
                        forceHLS: true,
                    },
                }}
                onReady={handleReady}
                onProgress={handleProgress}
                width="100%"

                height="100%"
            />
            <div className="absolute h-8  flex items-center space-x-2 justify-between w-full bg-base-100 opacity-50">
                <button onClick={togglePlay}>{playing ? <PauseIcon/> : <PlayIcon/>}</button>
                <input type="range" min="0" step={"any"} max="1" onChange={handleSeek} value={progress}
                       className="range w-full range-xs range-primary"/> <select
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
                    {isFullscreen ? <ShrinkIcon/> :<ExpandIcon/>}
                </button>
            </div>
        </div>
    );
};
