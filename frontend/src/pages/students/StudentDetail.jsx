import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import { studentService } from "../../services/studentService";
import { paymentService } from "../../services/paymentService";
import { Card } from "../../components/common/Card";
import { Badge } from "../../components/common/Badge";
import { Table } from "../../components/common/Table";
import { Button } from "../../components/common/Button";
import { HiOutlineArrowLeft, HiOutlinePencil } from "react-icons/hi";

export function StudentDetail() {
  const { id } = useParams();
  const [student, setStudent] = useState(null);
  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [studentData, paymentData] = await Promise.all([
          studentService.getById(id),
          paymentService.getByStudent(id),
        ]);
        setStudent(studentData);
        setPayments(Array.isArray(paymentData) ? paymentData : []);
      } catch (err) {
        console.error("Failed to load student detail", err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [id]);

  if (loading) {
    return (
      <div className="p-6">
        <p className="text-gray-500">Chargement...</p>
      </div>
    );
  }

  if (!student) {
    return (
      <div className="p-6">
        <p className="text-gray-500">Étudiant introuvable.</p>
        <Link to="/students" className="text-indigo-600 hover:underline mt-2 inline-block">Retour à la liste</Link>
      </div>
    );
  }

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-4">
        <Link to="/students" className="text-gray-400 hover:text-gray-600">
          <HiOutlineArrowLeft className="w-5 h-5" />
        </Link>
        <div>
          <h1 className="text-2xl font-bold text-gray-900">{student.firstName} {student.lastName}</h1>
          <p className="text-sm text-gray-500">{student.level || "Niveau non défini"}</p>
        </div>
        <div className="ml-auto flex gap-2">
          <Badge variant={student.status === "ACTIVE" ? "success" : "danger"}>{student.status}</Badge>
          <Button variant="outline" className="flex items-center gap-1">
            <HiOutlinePencil className="w-4 h-4" /> Modifier
          </Button>
        </div>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-3">Informations</h3>
          <dl className="space-y-2 text-sm">
            <div className="flex justify-between"><dt className="text-gray-500">Réf externe</dt><dd>{student.externalRef || "-"}</dd></div>
            <div className="flex justify-between"><dt className="text-gray-500">Niveau</dt><dd>{student.level || "-"}</dd></div>
            <div className="flex justify-between"><dt className="text-gray-500">Statut</dt><dd><Badge variant={student.status === "ACTIVE" ? "success" : "danger"}>{student.status}</Badge></dd></div>
            <div className="flex justify-between"><dt className="text-gray-500">Institution</dt><dd>{student.institutionName || "-"}</dd></div>
          </dl>
        </Card>

        <Card className="p-5 md:col-span-2">
          <h3 className="text-sm font-semibold text-gray-700 mb-3">Parents</h3>
          {student.parents?.length ? (
            <ul className="divide-y divide-gray-100">
              {student.parents.map((p) => (
                <li key={p.id} className="py-2 flex items-center gap-3">
                  <div className="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center text-xs font-semibold text-indigo-600">
                    {p.firstName?.[0]}{p.lastName?.[0]}
                  </div>
                  <div>
                    <p className="text-sm font-medium">{p.firstName} {p.lastName}</p>
                    <p className="text-xs text-gray-500">{p.email}</p>
                  </div>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-sm text-gray-400">Aucun parent lié</p>
          )}
        </Card>
      </div>

      <Card className="p-5">
        <h3 className="text-sm font-semibold text-gray-700 mb-3">Paiements récents</h3>
        <Table
          columns={[
            { key: "reference", label: "Référence" },
            { key: "amount", label: "Montant", render: (v) => `${v?.toLocaleString()} €` },
            { key: "method", label: "Méthode" },
            { key: "status", label: "Statut", render: (v) => <Badge variant={v === "COMPLETED" ? "success" : "warning"}>{v}</Badge> },
          ]}
          data={payments}
        />
      </Card>
    </div>

  );
}