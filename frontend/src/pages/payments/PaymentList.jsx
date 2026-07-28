import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { paymentService } from "../../services/paymentService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Badge } from "../../components/common/Badge";
import { Button } from "../../components/common/Button";
import { SearchBar } from "../../components/common/SearchBar";
import { Pagination } from "../../components/common/Pagination";
import { HiOutlinePlus } from "react-icons/hi";

export function PaymentList() {
  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetch = async () => {
      setLoading(true);
      try {
        const result = await paymentService.list({ search, page, size: 10 });
        const data = Array.isArray(result) ? result : result.content || [];
        setPayments(data);
        if (!Array.isArray(result)) setTotalPages(result.totalPages || 1);
      } catch (err) {
        console.error("Failed to load payments", err);
        setPayments([]);
      } finally {
        setLoading(false);
      }
    };
    fetch();
  }, [search, page]);

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Paiements</h1>
          <p className="text-sm text-gray-500 mt-1">Historique des transactions</p>
        </div>
        <Link to="/payments/new">
          <Button className="flex items-center gap-2"><HiOutlinePlus className="w-4 h-4" /> Nouveau paiement</Button>
        </Link>
      </div>
      <Card className="p-5">
        <div className="mb-4"><SearchBar placeholder="Rechercher un paiement..." value={search} onChange={setSearch} /></div>
        <Table
          columns={[
            { key: "reference", label: "Référence" },
            { key: "studentName", label: "Étudiant" },
            { key: "amount", label: "Montant", render: (v) => `${v?.toLocaleString()} €` },
            { key: "method", label: "Méthode" },
            { key: "status", label: "Statut", render: (v) => <Badge variant={v === "COMPLETED" ? "success" : "warning"}>{v}</Badge> },
          ]}
          data={payments}
          loading={loading}
        />
        {totalPages > 1 && <div className="mt-4"><Pagination current={page} total={totalPages} onChange={setPage} /></div>}
      </Card>
    </div>
  );
}