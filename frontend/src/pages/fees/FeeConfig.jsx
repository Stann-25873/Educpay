import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { feeService } from "../../services/feeService";
import { Card } from "../../components/common/Card";
import { Button } from "../../components/common/Button";
import { FeeForm } from "../../components/forms/FeeForm";
import { Table } from "../../components/common/Table";
import { Badge } from "../../components/common/Badge";

export function FeeConfig() {
  const navigate = useNavigate();
  const [fees, setFees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editing, setEditing] = useState(null);

  const fetchFees = async () => {
    setLoading(true);
    try {
      const data = await feeService.list();
      setFees(Array.isArray(data) ? data : data.content || []);
    } catch (err) {
      console.error(err);
      setFees([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchFees(); }, []);

  const handleSave = async (formData) => {
    try {
      if (editing) {
        await feeService.update(editing.id, formData);
      } else {
        await feeService.create(formData);
      }
      setShowForm(false);
      setEditing(null);
      fetchFees();
    } catch (err) {
      console.error("Failed to save fee", err);
    }
  };

  const handleEdit = (fee) => {
    setEditing(fee);
    setShowForm(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Supprimer ce frais ?")) return;
    try {
      await feeService.delete(id);
      fetchFees();
    } catch (err) {
      console.error("Failed to delete fee", err);
    }
  };

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Configuration des frais</h1>
          <p className="text-sm text-gray-500 mt-1">Définissez les frais de scolarité par niveau et période</p>
        </div>
        <Button onClick={() => { setEditing(null); setShowForm(!showForm); }}>
          {showForm ? "Annuler" : "Nouveau frais"}
        </Button>
      </div>

      {showForm && (
        <Card className="p-5">
          <h3 className="text-sm font-semibold text-gray-700 mb-4">
            {editing ? "Modifier le frais" : "Créer un nouveau frais"}
          </h3>
          <FeeForm initialData={editing} onSave={handleSave} onCancel={() => { setShowForm(false); setEditing(null); }} />
        </Card>
      )}

      <Card className="p-5">
        <h3 className="text-sm font-semibold text-gray-700 mb-4">Frais existants</h3>
        <Table
          columns={[
            { key: "code", label: "Code" },
            { key: "title", label: "Libellé" },
            { key: "amount", label: "Montant", render: (v) => `${v?.toLocaleString()} €` },
            { key: "billingPeriod", label: "Période" },
            { key: "level", label: "Niveau" },
            {
              key: "actions", label: "Actions",
              render: (_, row) => (
                <div className="flex gap-2">
                  <Button variant="outline" size="sm" onClick={() => handleEdit(row)}>Modifier</Button>
                  <Button variant="danger" size="sm" onClick={() => handleDelete(row.id)}>Supprimer</Button>
                </div>
              ),
            },
          ]}
          data={fees}
          loading={loading}
        />
      </Card>
    </div>
  );
}