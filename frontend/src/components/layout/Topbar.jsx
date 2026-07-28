import { HiOutlineSearch, HiOutlineBell, HiOutlineCog } from "react-icons/hi";
import { useAuth } from "../../hooks/useAuth";
import { getInitials } from "../../utils/formatters";

export function Topbar() {
  const { user, logout } = useAuth();

  return (
    <header className="sticky top-0 z-20 flex h-16 items-center justify-between border-b border-edu-border bg-white/80 px-6 backdrop-blur-xl">
      <div className="relative w-80">
        <HiOutlineSearch className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-edu-muted" />
        <input
          type="text"
          placeholder="Search..."
          className="input-field w-full rounded-xl bg-edu-surface py-2 pl-10 pr-4 text-sm"
        />
      </div>
      <div className="flex items-center gap-3">
        <button className="relative rounded-xl p-2 text-edu-text-secondary hover:bg-edu-surface-hover hover:text-edu-text-primary transition-colors">
          <HiOutlineBell className="h-5 w-5" />
          <span className="absolute right-1.5 top-1.5 h-2 w-2 rounded-full bg-rouge-500" />
        </button>
        <button className="rounded-xl p-2 text-edu-text-secondary hover:bg-edu-surface-hover hover:text-edu-text-primary transition-colors">
          <HiOutlineCog className="h-5 w-5" />
        </button>
        <div className="h-6 w-px bg-edu-border" />
        <div className="flex items-center gap-2 pl-1">
          <div className="flex h-8 w-8 items-center justify-center rounded-lg bg-edu-primary/10 text-sm font-semibold text-edu-primary">
            {user ? getInitials(`${user.firstName || ""} ${user.lastName || ""}`) : "?"}
          </div>
          <div className="hidden md:block">
            <p className="text-sm font-medium text-edu-text-primary">
              {user ? `${user.firstName || ""} ${user.lastName || ""}` : "User"}
            </p>
            <p className="text-xs text-edu-muted">
              {user?.roleName || user?.role || "N/A"}
            </p>
          </div>
      </div>
      </div>
    </header>
  );
}
