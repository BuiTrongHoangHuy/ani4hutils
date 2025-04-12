'use client'
export const FullScreenLoading = ({ isLoading }: { isLoading: boolean }) => {
    return (
        <div
            className={`${isLoading ? " " : "hidden"} fixed inset-0 flex items-center justify-center  bg-opacity-70 z-[50]`}
        >
            <span className="loading loading-spinner loading-xl"></span>
        </div>
    );
};