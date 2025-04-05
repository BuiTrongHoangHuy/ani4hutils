package common

type ResolutionInfo struct {
	Width     int
	Height    int
	VBitRate  int
	ABitRate  int
	BandWidth int
}

var ResolutionMap = map[int]ResolutionInfo{
	360:  {Width: 480, Height: 360, VBitRate: 1000, ABitRate: 96, BandWidth: 1100000},
	480:  {Width: 640, Height: 480, VBitRate: 1500, ABitRate: 128, BandWidth: 1650000},
	720:  {Width: 1280, Height: 720, VBitRate: 2500, ABitRate: 128, BandWidth: 2700000},
	1080: {Width: 1920, Height: 1080, VBitRate: 5000, ABitRate: 192, BandWidth: 5300000},
}
