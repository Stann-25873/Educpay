export function Badge({ children, ...props }) {
  return (
    <span {...props}>
      {children}
    </span>
  );
}

