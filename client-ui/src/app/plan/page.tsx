'use client';

import { useEffect, useState } from 'react';
import { SubscriptionPlan, createPayment, getSubscriptionPlans } from './service';
import { useRouter } from 'next/navigation';

export default function PlanPage() {
    const [plan, setPlan] = useState<SubscriptionPlan | null>(null);
    const [loading, setLoading] = useState(true);
    const router = useRouter();
    const [isLogin, setIsLogin] = useState<boolean>(false)
    const [userId, setUserId] = useState<string>('');
    useEffect(() => {
        const fetchPlans = async () => {
            try {
                const data = await getSubscriptionPlans();
                setPlan(data.data.at(0) || null);
            } catch (error) {
                console.error('Error fetching plans:', error);
            } finally {
                setLoading(false);
            }
        };
        fetch("/api/me", {
            method: "GET"
        }).then(r => {
            if (r.status == 200) {
                setIsLogin(true)
                r.json().then(data => {
                    setUserId(data.userId)
                })
            }else{
                router.push('/');
            }
        })
        fetchPlans();
    }, []);

    const handleSubscribe = async (plan: SubscriptionPlan) => {
        if (!isLogin) {
            router.push('/login');
            return;
        }

        try {
            const response = await createPayment({
                userId: userId,
                price: plan.price,
            });
            console.log(" url Payment", response.data.urlPayment);
            // Redirect to payment URL
            window.location.href = response.data.urlPayment || '/';
        } catch (error) {
            console.error('Error creating payment:', error);
        }
    };

    if (loading) {
        return (
            <div className="flex items-center justify-center">
                <div className="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
            </div>
        );
    }

    return (
        <div className={"w-screen mt-[64px] pl-20 pr-20 pt-8"}>
        <div className=" bg-base-100 py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-7xl mx-auto">
                <div className="text-center">
                    <h2 className="text-3xl font-extrabold text-white sm:text-4xl">
                        Choose Your Plan
                    </h2>
                    <p className="mt-4 text-lg text-white">
                        Select the perfect plan for your streaming needs
                    </p>
                </div>

                <div className="mt-12 flex items-center justify-center gap-8 ">
                    { plan &&
                        <div
                            key={plan.id}
                            className="bg-base-200 rounded-lg shadow-lg overflow-hidden hover:shadow-xl transition-shadow duration-300"
                        >
                            <div className="px-6 py-8">
                                <h3 className="text-2xl font-bold text-white">{plan.name}</h3>
                                <p className="mt-4 text-4xl font-extrabold text-white">
                                    {plan.price} VND
                                    <span className="text-base font-medium text-white">/month</span>
                                </p>
                                <ul className="mt-6 space-y-4">
                                    <li className="flex items-center">
                                        <svg
                                            className="h-5 w-5 text-green-500"
                                            fill="none"
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth="2"
                                            viewBox="0 0 24 24"
                                            stroke="currentColor"
                                        >
                                            <path d="M5 13l4 4L19 7"></path>
                                        </svg>
                                        <span className="ml-3 text-white">
                      {plan.resolution} Resolution
                    </span>
                                    </li>
                                    <li className="flex items-center">
                                        <svg
                                            className="h-5 w-5 text-green-500"
                                            fill="none"
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth="2"
                                            viewBox="0 0 24 24"
                                            stroke="currentColor"
                                        >
                                            <path d="M5 13l4 4L19 7"></path>
                                        </svg>
                                        <span className="ml-3 text-white">
                      {plan.quality} Quality
                    </span>
                                    </li>
                                    <li className="flex items-center">
                                        <svg
                                            className="h-5 w-5 text-green-500"
                                            fill="none"
                                            strokeLinecap="round"
                                            strokeLinejoin="round"
                                            strokeWidth="2"
                                            viewBox="0 0 24 24"
                                            stroke="currentColor"
                                        >
                                            <path d="M5 13l4 4L19 7"></path>
                                        </svg>
                                        <span className="ml-3 text-white">
                      {plan.maxSimultaneousStreams} Devices
                    </span>
                                    </li>
                                </ul>
                                <button
                                    onClick={() => handleSubscribe(plan)}
                                    className="mt-8 w-full bg-primary text-white py-3 px-4 rounded-md hover:bg-primary/90 cursor-pointer focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 transition-colors duration-200"
                                >
                                    Subscribe Now
                                </button>
                            </div>
                        </div>
                    }

                </div>
            </div>
        </div>
        </div>
    );
}