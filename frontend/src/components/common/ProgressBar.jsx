export function ProgressBar({ value = 0, ...props }) {
  return (
    <div {...props}>
      {value}
    </div>
  );
}

