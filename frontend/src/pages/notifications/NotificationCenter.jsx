import { useState, useEffect } from "react";
import { notificationService } from "../../services/notificationService";
import { Card } from "../../components/common/Card";
import { Badge } from "../../components/common/Badge";
import { Button } from "../../components/common/Button";
import { HiOutlineBell, HiOutlineCheck } from "react-icons/hi";

export function NotificationCenter() {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetch = async () => {
    setLoading(true);
    try {
      const data = await notificationService.list();
      setNotifications(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error("Failed to load notifications", err);
      setNotifications([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetch(); }, []);

  const handleMarkAsRead = async (id) => {
    try {
      await notificationService.markAsRead(id);
      setNotifications((prev) =>
        prev.map((n) => (n.id === id ? { ...n, read: true } : n))
      );
    } catch (err) {
      console.error("Failed to mark as read", err);
    }
  };

  return (
    <div className="p-6 space-y-6">
      <div className="flex items-center gap-3">
        <HiOutlineBell className="w-8 h-8 text-indigo-600" />
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Notifications</h1>
          <p className="text-sm text-gray-500 mt-1">Centre de notifications</p>
        </div>
      <Card className="p-5">
        {loading ? (
          <p className="text-gray-500">Chargement...</p>
        ) : notifications.length === 0 ? (
          <p className="text-gray-400 text-center py-8">Aucune notification</p>
        ) : (
          <ul className="divide-y divide-gray-100">
            {notifications.map((n) => (
              <li key={n.id} className={`py-3 flex items-start gap-3 ${!n.read ? 'bg-indigo-50 -mx-5 px-5' : ''}`}>
                <div className={`w-2 h-2 mt-2 rounded-full flex-shrink-0 ${n.read ? 'bg-gray-300' : 'bg-indigo-600'}`} />
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-medium text-gray-900">{n.title}</p>
                  <p className="text-xs text-gray-500 mt-0.5">{n.message}</p>
                  <p className="text-xs text-gray-400 mt-1">{n.createdAt ? new Date(n.createdAt).toLocaleString() : ""}</p>
                </div>
                {!n.read && (
                  <Button variant="ghost" size="sm" onClick={() => handleMarkAsRead(n.id)} className="flex-shrink-0">
                    <HiOutlineCheck className="w-4 h-4" />
                  </Button>
                )}
              </li>
            ))}
          </ul>
        )}
      </Card>
    </div>
    </div>
  );
}