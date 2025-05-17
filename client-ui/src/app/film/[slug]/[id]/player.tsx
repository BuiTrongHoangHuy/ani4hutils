'use client'

import ReactPlayer from "react-player";
import {ChangeEvent, useEffect, useRef, useState, KeyboardEvent} from "react";
import {OnProgressProps} from "react-player/types/base";
import {ExpandIcon, PauseIcon, PlayIcon, ShrinkIcon, Volume2, VolumeX, SkipForward, CircleGauge, RotateCcw, RotateCw} from "lucide-react";
import {formatTime} from "@/utils/format";
import {Episode} from "@/types/episode";
import {FilmService} from "@/app/film/[slug]/[id]/service";

export default function Player({filmId ,episodeNumber}:{filmId: string,episodeNumber: number}) {
    const player = useRef<ReactPlayer>(null);
    const playerWrapper = useRef<HTMLDivElement  | null>(null);
    const [levels, setLevels] = useState([]);
    const [playing, setPlaying] = useState(true);
    const [progress, setProgress] = useState(0);
    const [duration, setDuration] = useState(0);
    const [isFullscreen, setIsFullscreen] = useState(false);
    const [muted, setMuted] = useState(false)
    const [playedTime, setPlayedTime] = useState(0);
    const [loaded, setLoaded] = useState(0);
    const [playbackRate, setPlaybackRate] = useState(1);
    const [showControls, setShowControls] = useState(true);
    const hideControlsTimeout = useRef<NodeJS.Timeout | null>(null);
    const [selectedLevel, setSelectedLevel] = useState(-1);
    const [volume, setVolume] = useState(1);
    const [previousVolume, setPreviousVolume] = useState(1)
    const [showVolumeSlider, setShowVolumeSlider] = useState(false);
    const [episode, setEpisode] = useState<Episode>({} as Episode);
    const handleReady = () => {
        const hls = player.current?.getInternalPlayer("hls");
        if (!player.current || !hls ) return
        if (hls && hls.levels) {
            setLevels(hls.levels);
            setSelectedLevel(hls.currentLevel)
           hls.currentLevel = -1;
        }
        const onLevelSwitched = (_: string, data: { level: number }) => {
            setSelectedLevel(data.level);
        };
        hls.on("hlsLevelSwitched", onLevelSwitched);

        return () => {
            hls.off("hlsLevelSwitched", onLevelSwitched);
        };
        //setDuration(player.current.getDuration());
    };
    const onChangeBitrate = (e: ChangeEvent<HTMLSelectElement>) => {
        const selectedLevels = e.target.value;
        const hls = player.current?.getInternalPlayer("hls");
        try{
            if (hls) {
                hls.currentLevel = Number(selectedLevels);
                setSelectedLevel(Number(selectedLevels));
            }
        }catch (e){
            console.log(e)
        }

    };
    const togglePlay = () => {
        setPlaying(!playing);
    };

    const handleProgress = (state:  OnProgressProps) => {
        try{
            const hls = player.current?.getInternalPlayer("hls");
            const currentLevel = hls?.currentLevel;

            console.log("Resolution:", hls?.levels[currentLevel].height + "p");
            console.log("Bitrate:", hls?.levels[currentLevel].bitrate / 1000 + " kbps");
            console.log("loaded", state.loadedSeconds)
            console.log("played", state.playedSeconds)
            setProgress(state.played);
            setLoaded(state.loaded);
            setPlayedTime(state.playedSeconds)
            //console.log(state.played)
        }catch (e){
            console.log(e)
        }

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
        if (muted) {
            setVolume(previousVolume > 0 ? previousVolume : 1)
        } else {
            setPreviousVolume(volume);
            setVolume(0);
        }
    }
    const handleVolumeChange = (e: ChangeEvent<HTMLInputElement>) => {
        const newVolume = parseFloat(e.target.value);
        setVolume(newVolume);
        setPreviousVolume(newVolume);
        setMuted(newVolume === 0);
    };
    const handleDuration = (duration:number) =>{
        setDuration(duration)
    }

    const handleMouseMove = () => {
        setShowControls(true);
        if (hideControlsTimeout.current) {
            clearTimeout(hideControlsTimeout.current);
        }
        hideControlsTimeout.current = setTimeout(() => {
            setShowControls(false);
        }, 3000);
    };

    const handleMouseLeave = () => {
        if (hideControlsTimeout.current) {
            clearTimeout(hideControlsTimeout.current);
        }
        hideControlsTimeout.current = setTimeout(() => {
            setShowControls(false);
        }, 3000);
    };
    const rewind5Seconds = () => {
        if (!player.current) return;
        const currentTime = player.current.getCurrentTime();
        const newTime = Math.max(0, currentTime - 5);
        player.current.seekTo(newTime, "seconds");
        setPlayedTime(newTime);
        setProgress(newTime / duration);
    };

    const fastForward5Seconds = () => {
        if (!player.current) return;
        const currentTime = player.current.getCurrentTime();
        const newTime = Math.min(duration, currentTime + 5);
        player.current.seekTo(newTime, "seconds");
        setPlayedTime(newTime);
        setProgress(newTime / duration);
    };
    const handlePlayerClick = (e: React.MouseEvent<HTMLDivElement>) => {

        const target = e.target as HTMLElement;
        if (
            target.closest("button") ||
            target.closest("input") ||
            target.closest("select") ||
            target.closest(".dropdown")
        ) {
            return;
        }
        togglePlay();
        setShowControls(true);
        handleMouseLeave();
    };
    const handleControlsClick = (e: React.MouseEvent<HTMLDivElement>) => {
        e.stopPropagation();
    };
    const handleSkipClick = () =>{
        if (!player.current) return;
        const currentTime = player.current.getCurrentTime();
        const newTime = Math.min(duration, currentTime + 150);
        player.current.seekTo(newTime, "seconds");
        setPlayedTime(newTime);
        setProgress(newTime / duration);
    }


    useEffect(() => {
        const handleKeyDown = (e: globalThis.KeyboardEvent) => {
            if (
                document.activeElement instanceof HTMLInputElement ||
                document.activeElement instanceof HTMLSelectElement ||
                document.activeElement instanceof HTMLTextAreaElement
            ) {
                return;
            }

            setShowControls(true);
            if (hideControlsTimeout.current) {
                clearTimeout(hideControlsTimeout.current);
            }
            hideControlsTimeout.current = setTimeout(() => {
                setShowControls(false);
            }, 3000);

            switch (e.key) {
                case 'ArrowRight':
                    fastForward5Seconds();
                    break;
                case 'ArrowLeft':
                    rewind5Seconds();
                    break;
                case 'f':
                case 'F':
                    toggleFullscreen();
                    break;
                default:
                    break;
            }
        };
        window.addEventListener('keydown', handleKeyDown);

        return () => {
            if (hideControlsTimeout.current) {
                clearTimeout(hideControlsTimeout.current);
            }
            window.removeEventListener('keydown', handleKeyDown);
        };
    }, [fastForward5Seconds, rewind5Seconds, toggleFullscreen]);
    useEffect(() => {
        const getEpisodeDate = async () => {
            const episode = await FilmService.getEpisodeByEpisodeNumber(filmId, episodeNumber);
            const episodeData: Episode = await episode.data;
            setEpisode((prev) => {
                if (prev?.videoUrl === episodeData?.videoUrl) return prev;
                return episodeData;
            });
            setPlaying(true);
        };
        getEpisodeDate();
    }, [filmId, episodeNumber]);
    if (!episode?.videoUrl) return ;
    return (
        <div className="player-wrapper" ref={playerWrapper}
             onMouseMove={handleMouseMove}
             onMouseLeave={handleMouseLeave}
             onClick={handlePlayerClick}
        >
            <ReactPlayer
                ref={player}
                url={episode.videoUrl}
                playing={playing}
                muted={muted}
                volume={volume}
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
                height="480px"
            />
            <div className={`absolute bottom-0 left-0 right-0 px-4 pb-2 pt-4 flex items-center text-white
            gap-5 justify-between w-full bg-base-100 transition-all duration-500 ease-in-out
            ${showControls || !playing ? "opacity-70 transform translate-y-0":"opacity-0 transform translate-y-2 pointer-events-none"}
            `}
                onClick={handleControlsClick}
            >
                <input type="range" min="0" step={"any"} max="1" value={loaded} readOnly={true}
                       className="absolute top-[-4] left-0 right-0 w-full
                       range [--range-thumb:var(--color-white)]
                       [--range-thumb-size:calc(var(--size-selector,0.25rem)*2)]
                       [--range-bg:gray]
                        "/>

                <input type="range" min="0" step={"any"} max="1" onChange={handleSeek} value={progress}
                       className="absolute top-[-4] left-0 right-0 w-full range-primary
                       range [--range-thumb:var(--color-primary)] cursor-pointer
                       [--range-thumb-size:calc(var(--size-selector,0.25rem)*2)]
                       [--range-bg:transparent]
                        "/>

                <button className="cursor-pointer hover:text-primary tooltip" data-tip={`${playing ? "Pause" : "Play"}`}
                        onClick={togglePlay}>{playing ? <PauseIcon size={20}/> : <PlayIcon size={20}/>}</button>

                <div className="flex justify-center items-center gap-3"
                     onMouseEnter={() => setShowVolumeSlider(true)}
                     onMouseLeave={() => setShowVolumeSlider(false)}
                >
                    <button  onClick={toggleMute} className="cursor-pointer hover:text-primary tooltip"
                             data-tip={`${muted ? "Un Mute" : "Mute"}`}>
                        {muted ? <VolumeX size={20}/> : <Volume2 size={20}/> }
                    </button>
                    {showVolumeSlider && (
                        <input
                            type="range"
                            min="0"
                            max="1"
                            step="0.01"
                            value={volume}
                            onChange={handleVolumeChange}
                            className=" w-24  range-primary range
                            [--range-thumb-size:calc(var(--size-selector,0.25rem)*1.5)]
                            [--range-bg:gray] cursor-pointer"
                        />
                    )}
                </div>
                <span className="text-sm ">{formatTime(playedTime)} / {formatTime(duration)}</span>
                <div className="flex-1"></div>
                <button className="cursor-pointer hover:text-primary tooltip" data-tip="Rewind 5s" onClick={rewind5Seconds} >
                    <RotateCcw size={20}/>
                </button>
                <button className="cursor-pointer hover:text-primary tooltip" data-tip="Fast Forward 5s" onClick={fastForward5Seconds} >
                    <RotateCw size={20}/>
                </button>
                <button className="cursor-pointer hover:text-primary tooltip" data-tip="Skip OP/ED" onClick={handleSkipClick} >
                    <SkipForward size={20}/>
                </button>
                <div className="relative flex justify-center items-center">
                    <div className="dropdown dropdown-top p-0">
                        <div tabIndex={0} role="button" className="btn hover:text-primary p-0 tooltip" data-tip="Playback Rate"><CircleGauge size={20}/></div>
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
                onChange={onChangeBitrate}
                value={selectedLevel}
                >
                    <option value={-1}>Auto</option>
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
                <button onClick={toggleFullscreen} className="cursor-pointer hover:text-primary tooltip" data-tip={`${isFullscreen ? "Exit Fullscreen" : "Fullscreen"}`} >
                    {isFullscreen ? <ShrinkIcon size={20}/> :<ExpandIcon size={20}/>}
                </button>
            </div>
        </div>
    );
};
