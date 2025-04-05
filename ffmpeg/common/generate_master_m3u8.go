package common

import (
	"fmt"
	"os"
	"sort"
	"strings"
)

func SaveToFile(content, path string) error {
	return os.WriteFile(path, []byte(content), 0644)
}
func GenerateMasterM3U8(maxRes int) string {
	lines := []string{"#EXTM3U", "#EXT-X-VERSION:7"}
	var resolutions []int

	for res := range ResolutionMap {
		if res <= maxRes {
			resolutions = append(resolutions, res)
		}
	}
	sort.Ints(resolutions)

	for _, res := range resolutions {
		info := ResolutionMap[res]
		codec := "avc1.42c01e"
		if res >= 720 {
			codec = "avc1.42c01f"
		}

		// Tính Average Bandwidth (bps)
		avgBandwidth := (info.VBitRate + info.ABitRate) * 1000

		lines = append(lines, fmt.Sprintf(
			`#EXT-X-STREAM-INF:BANDWIDTH=%d,AVERAGE-BANDWIDTH=%d,RESOLUTION=%dx%d,CODECS="%s,mp4a.40.2"`,
			info.BandWidth,
			avgBandwidth,
			info.Width,
			info.Height,
			codec,
		))
		lines = append(lines, fmt.Sprintf("index-%dp.m3u8", res))
	}

	return strings.Join(lines, "\n") + "\n"
}
