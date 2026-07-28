import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import { SIDEBAR_ITEMS } from "../../utils/constants";
import {
  HiOutlineChartBar,
  HiOutlineUserGroup,
  HiOutlineAcademicCap,
  HiOutlineCurrencyDollar,
  HiOutlineCreditCard,
  HiOutlineDocumentText,
  HiOutlineExclamationCircle,
  HiOutlineChartPie,
  HiOutlineBell,
  HiOutlineCog,
} from "react-icons/hi";

const iconMap = {
  HiOutlineChartBar,
  HiOutlineUserGroup,
  HiOutlineAcademicCap,
  HiOutlineCurrencyDollar,
  HiOutlineCreditCard,
  HiOutlineDocumentText,
  HiOutlineExclamationCircle,
  HiOutlineChartPie,
  HiOutlineBell,
  HiOutlineCog,
};

export function Sidebar() {
  const { hasAnyRole } = useAuth();

  const filteredItems = SIDEBAR_ITEMS.filter((item) => hasAnyRole(item.roles));

  return (
    <aside className="fixed left-0 top-0 z-30 flex h-screen w-64 flex-col border-r border-edu-border bg-white">
      <div className="flex h-16 items-center gap-3 border-b border-edu-border px-6">
        <div className="flex h-9 w-9 items-center justify-center rounded-xl bg-edu-primary">
          <span className="text-lg font-bold text-white">E</span>
        </div>
        <div>
          <h1 className="text-base font-bold text-edu-text-primary">EduPay</h1>
          <p className="text-[11px] font-medium text-edu-muted">School Finance Platform</p>
        </div>
      <nav className="flex-1 overflow-y-auto px-4 py-4">
        <ul className="space-y-1">
          {filteredItems.map((item) => {
            const Icon = iconMap[item.icon] || HiOutlineChartBar;
            return (
              <li key={item.path}>
                <NavLink
                  to={item.path}
                  end={item.path === "/dashboard"}
                  className={({ isActive }) =>
                    `flex items-center gap-3 rounded-xl px-3 py-2.5 text-sm font-medium transition-all duration-200 ${
                      isActive
                        ? "bg-edu-primary/10 text-edu-primary font-semibold"
                        : "text-edu-text-secondary hover:bg-edu-primary/5 hover:text-edu-primary"
                    }`
                  }
                >
                  <Icon className="h-5 w-5 flex-shrink-0" />
                  <span>{item.label}</span>
                </NavLink>
              </li>
            );
          })}
        </ul>
      </nav>
      <div className="border-t border-edu-border px-4 py-4">
        <div className="rounded-xl bg-edu-primary/5 px-3 py-2.5">
          <p className="text-xs font-medium text-edu-primary">EduPay v1.0</p>
          <p className="text-[11px] text-edu-muted">Multi-tenant SaaS</p>
        </div>
        </div>
        </div>
    </aside>
  );
}
