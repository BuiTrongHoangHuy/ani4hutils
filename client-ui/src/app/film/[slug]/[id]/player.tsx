'use client'

import ReactPlayer from "react-player";
import {ChangeEvent, useRef, useState} from "react";
import {OnProgressProps} from "react-player/types/base";
import {ExpandIcon, PauseIcon, PlayIcon, ShrinkIcon, Volume2, VolumeX, SkipForward, Settings} from "lucide-react";
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
    const [playbackRate, setPlaybackRate] = useState(1);

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
        const hls = player.current?.getInternalPlayer("hls");
        const currentLevel = hls?.currentLevel;

        console.log("Resolution:", hls?.levels[currentLevel].height + "p");
        console.log("Bitrate:", hls?.levels[currentLevel].bitrate / 1000 + " kbps");
        console.log("loaded", state.loadedSeconds)
        console.log("played", state.playedSeconds)
        setProgress(state.played);
        setPlayedTime(state.playedSeconds)
        //console.log(state.played)
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
                        hlsOptions:{
                            maxBufferLength:30,
                            maxMaxBufferLength: 60,
                            liveSyncDuration: 15,
                            lowLatencyMode:true,
                        }
                    },
                }}
                onDuration={handleDuration}
                playbackRate={playbackRate}
                onReady={handleReady}
                onProgress={handleProgress}
                width="100%"
                height="100%"
            />
            <div className="absolute bottom-0 left-0 right-0 px-4 pb-2 pt-4 flex items-center text-white
            gap-5 justify-between w-full bg-base-100 opacity-50">
                <input type="range" min="0" step={"any"} max="1" onChange={handleSeek} value={progress}
                       className="absolute top-[-4] left-0 right-0 w-full range-primary
                       range [--range-thumb:var(--color-primary)] cursor-pointer
                       [--range-thumb-size:calc(var(--size-selector,0.25rem)*2)]
                       hover:[--range-thumb-size:calc(var(--size-selector,0.25rem)*2.5)]
                       [--range-bg:gray]
                        "/>
                <button className="cursor-pointer hover:text-primary" onClick={togglePlay}>{playing ? <PauseIcon size={20}/> : <PlayIcon size={20}/>}</button>
                <button  onClick={toggleMute} className="cursor-pointer hover:text-primary" >
                    {muted ? <VolumeX size={20}/> : <Volume2 size={20}/> }
                </button>
                <span className="text-sm ">{formatTime(playedTime)} / {formatTime(duration)}</span>
                <div className="flex-1"></div>
                <button className="cursor-pointer hover:text-primary"  >
                    <SkipForward size={20}/>
                </button>
                <div className="relative flex justify-center items-center">
                    <div className="dropdown dropdown-top p-0">
                        <div tabIndex={0} role="button" className="btn hover:text-primary p-0"><Settings size={20}/></div>
                        <ul tabIndex={0} className="dropdown-content menu bg-black rounded z-1  p-2">
                            {[0.25 ,0.5, 0.75, 1, 1.25, 1.5, 2].map(rate => (
                                <li
                                    key={rate}
                                    className={`cursor-pointer px-2 py-1 hover:bg-white/10 rounded ${rate === playbackRate ? 'font-bold text-primary' : ''}`}
                                    onClick={() => {
                                        setPlaybackRate(rate);
                                    }}
                                >
                                    {rate}x
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
                <select
                    className="cursor-pointer hover:border-primary bg-base-100 border border-white px-1 text-sm rounded flex justify-center items-center pb-1"
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
                <button onClick={toggleFullscreen} className="cursor-pointer hover:text-primary" >
                    {isFullscreen ? <ShrinkIcon size={20}/> :<ExpandIcon size={20}/>}
                </button>
            </div>
        </div>
    );
};
