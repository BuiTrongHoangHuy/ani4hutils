package common

type ResolutionInfo struct {
	Width  int
	Height int
}

var ResolutionMap = map[int]ResolutionInfo{
	360:  {Width: 640, Height: 360},
	480:  {Width: 640, Height: 480},
	720:  {Width: 1280, Height: 720},
	1080: {Width: 1920, Height: 1080},
}
