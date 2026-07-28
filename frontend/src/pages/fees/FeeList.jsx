import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { feeService } from "../../services/feeService";
import { Card } from "../../components/common/Card";
import { Table } from "../../components/common/Table";
import { Button } from "../../components/common/Button";
import { SearchBar } from "../../components/common/SearchBar";
import { Pagination } from "../../components/common/Pagination";
import { HiOutlinePlus } from "react-icons/hi";

export function FeeList() {
  const [fees, setFees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  useEffect(() => {
    const fetchFees = async () => {
      setLoading(true);
      try {
        const result = await feeService.list({ search, page, size: 10 });
        const data = Array.isArray(result) ? result : result.content || [];
        setFees(data);
        if (!Array.isArray(result)) setTotalPages(result.totalPages || 1);
      } catch (err) {
        console.error("Failed to load fees", err);
        setFees([]);
      } finally {
        setLoading(false);
      }
    };
    fetchFees();
  }, [search, page]);

  const columns = [
    { key: "code", label: "Code" },
    { key: "title", label: "Libellé" },
    { key: "amount", label: "Montant", render: (v) => `${v?.toLocaleString()} €` },
    { key: "billingPeriod", label: "Période" },
    { key: "level", label: "Niveau" },
  ];

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Frais</h1>
          <p className="text-sm text-gray-500 mt-1">Gérez la configuration des frais de scolarité</p>
        </div>
        <Link to="/fees/config">
          <Button className="flex items-center gap-2"><HiOutlinePlus className="w-4 h-4" /> Configurer</Button>
        </Link>
      </div>
      <Card className="p-5">
        <div className="mb-4"><SearchBar placeholder="Rechercher un frais..." value={search} onChange={setSearch} /></div>
        <Table columns={columns} data={fees} loading={loading} />
        {totalPages > 1 && <div className="mt-4"><Pagination current={page} total={totalPages} onChange={setPage} /></div>}
      </Card>
    </div>
  );
}