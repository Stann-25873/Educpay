import { useState, useEffect } from "react";
import { useAuth } from "../../hooks/useAuth";
import { reportService } from "../../services/reportService";
import { studentService } from "../../services/studentService";
import { paymentService } from "../../services/paymentService";
import { Card } from "../../components/common/Card";
import { RevenueChart } from "../../components/charts/RevenueChart";
import { FeeDistributionChart } from "../../components/charts/FeeDistributionChart";
import { PaymentProgressChart } from "../../components/charts/PaymentProgressChart";
import { HiOutlineUsers, HiOutlineCash, HiOutlineAcademicCap, HiOutlineExclamationCircle } from "react-icons/hi";

export function Dashboard() {
  const { user } = useAuth();
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const [reportData, students, payments] = await Promise.all([
          reportService.getDashboard(),
          studentService.list({ limit: 5 }),
          paymentService.list({ limit: 5 }),
        ]);
        setStats({
          totalStudents: reportData.totalStudents || 0,
          totalRevenue: reportData.totalRevenue || 0,
          pendingFees: reportData.pendingFees || 0,
          overdueCount: reportData.overdueCount || 0,
          revenueData: reportData.revenueData || [],
          distributionData: reportData.distributionData || [],
          progressData: reportData.progressData || [],
          recentStudents: Array.isArray(students) ? students : students?.content || [],
          recentPayments: Array.isArray(payments) ? payments : payments?.content || [],
        });
      } catch (err) {
        console.error("Failed to load dashboard stats", err);
        setStats({
          totalStudents: 0,
          totalRevenue: 0,
          pendingFees: 0,
          overdueCount: 0,
          revenueData: [],
          distributionData: [],
          progressData: [],
          recentStudents: [],
          recentPayments: [],
        });
      } finally {
        setLoading(false);
      }
    };
    fetchStats();
  }, []);

  const summaryCards = [
    { label: "Étudiants", value: stats?.totalStudents ?? 0, icon: HiOutlineAcademicCap, color: "bg-indigo-500" },
    { label: "Revenus", value: `${(stats?.totalRevenue ?? 0).toLocaleString()} €`, icon: HiOutlineCash, color: "bg-green-500" },
    { label: "Impayés", value: (stats?.pendingFees ?? 0).toLocaleString(), icon: HiOutlineExclamationCircle, color: "bg-red-500" },
    { label: "Retards", value: (stats?.overdueCount ?? 0).toLocaleString(), icon: HiOutlineUsers, color: "bg-amber-500" },
  ];

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Tableau de bord</h1>
        <p className="text-sm text-gray-500 mt-1">
          Bienvenue, {user?.firstName || user?.email || "Utilisateur"}
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
        {summaryCards.map((card) => (
          <Card key={card.label} className="p-5">
            <div className="flex items-center gap-4">
              <div className={`w-12 h-12 ${card.color} rounded-xl flex items-center justify-center`}>
                <card.icon className="w-6 h-6 text-white" />
              </div>
              <div>
                <p className="text-sm text-gray-500">{card.label}</p>
                <p className="text-xl font-bold text-gray-900">{card.value}</p>
              </div>
            </div>
          </Card>
        ))}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-4">Évolution des revenus</h3>
          <RevenueChart data={stats?.revenueData || []} />
        </Card>
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-4">Répartition des frais</h3>
          <FeeDistributionChart data={stats?.distributionData || []} />
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-4">Progression des paiements</h3>
          <PaymentProgressChart data={stats?.progressData || []} />
        </Card>

        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-4">Derniers étudiants</h3>
          {loading ? (
            <p className="text-sm text-gray-400">Chargement...</p>
          ) : (
            <ul className="divide-y divide-gray-100">
              {stats?.recentStudents?.map((s) => (
                <li key={s.id} className="py-2 flex items-center gap-3">
                  <div className="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center text-xs font-semibold text-indigo-600">
                    {s.firstName?.[0]}{s.lastName?.[0]}
                  </div>
                  <div>
                    <p className="text-sm font-medium text-gray-900">{s.firstName} {s.lastName}</p>
                    <p className="text-xs text-gray-500">{s.level || "N/A"}</p>
                  </div>
                </li>
              ))}
            </ul>
          )}
        </Card>

        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-4">Derniers paiements</h3>
          {loading ? (
            <p className="text-sm text-gray-400">Chargement...</p>
          ) : (
            <ul className="divide-y divide-gray-100">
              {stats?.recentPayments?.map((p) => (
                <li key={p.id} className="py-2 flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-gray-900">{p.studentName || "N/A"}</p>
                    <p className="text-xs text-gray-500">{p.method || "N/A"}</p>
                  </div>
                  <span className="text-sm font-semibold text-green-600">
                    {p.amount?.toLocaleString()} €
                  </span>
                </li>
              ))}
            </ul>
          )}
        </Card>
    </div>
  </div>
  
  );
}