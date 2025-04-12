import PlayerWrapper from "@/app/film/[slug]/[id]/PlayerWrapper";

export default async function  Page({
                                  params,
                              }: {
    params: Promise<{ id: string }>
}) {
    const { id } = await params
    return (
        <div className={"w-screen mt-[64px] px-20 py-10 space-y-20"}>
            <PlayerWrapper></PlayerWrapper>
        </div>
    )
}