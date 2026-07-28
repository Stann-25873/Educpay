import { useState, useEffect } from "react";
import { reportService } from "../../services/reportService";
import { Card } from "../../components/common/Card";
import { RevenueChart } from "../../components/charts/RevenueChart";
import { FeeDistributionChart } from "../../components/charts/FeeDistributionChart";
import { PaymentProgressChart } from "../../components/charts/PaymentProgressChart";

export function ReportsDashboard() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    reportService.getDashboard()
      .then(setData)
      .catch(console.error)
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="p-6"><p className="text-gray-500">Chargement...</p></div>;

  return (
    <div className="p-6 space-y-6">
      <div>
        <h1 className="text-2xl font-bold text-gray-900">Rapports</h1>
        <p className="text-sm text-gray-500 mt-1">Analyse financière et indicateurs clés</p>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <Card className="p-5">
          <p className="text-sm text-gray-500">Revenus totaux</p>
          <p className="text-2xl font-bold text-gray-900">{(data?.totalRevenue || 0).toLocaleString()} €</p>
        </Card>
        <Card className="p-5">
          <p className="text-sm text-gray-500">Frais impayés</p>
          <p className="text-2xl font-bold text-red-600">{(data?.pendingFees || 0).toLocaleString()} €</p>
        </Card>
        <Card className="p-5">
          <p className="text-sm text-gray-500">Étudiants actifs</p>
          <p className="text-2xl font-bold text-indigo-600">{data?.totalStudents || 0}</p>
        </Card>
      </div>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card className="p-5"><h3 className="text-sm font-semibold text-gray-700 mb-4">Revenus mensuels</h3><RevenueChart data={data?.revenueData || []} /></Card>
        <Card className="p-5"><h3 className="text-sm font-semibold text-gray-700 mb-4">Répartition</h3><FeeDistributionChart data={data?.distributionData || []} /></Card>
      </div>
      <Card className="p-5"><h3 className="text-sm font-semibold text-gray-700 mb-4">Progression</h3><PaymentProgressChart data={data?.progressData || []} /></Card>
    </div>
  );
}