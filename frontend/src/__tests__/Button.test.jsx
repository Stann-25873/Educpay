import { render, screen, fireEvent } from "@testing-library/react";
import { Button } from "../components/common/Button";

describe("Button Component", () => {
  it("renders with text", () => {
    render(<Button>Cliquez ici</Button>);
    expect(screen.getByText("Cliquez ici")).toBeInTheDocument();
  });

  it("calls onClick handler", () => {
    const handleClick = jest.fn();
    render(<Button onClick={handleClick}>Action</Button>);
    fireEvent.click(screen.getByText("Action"));
    expect(handleClick).toHaveBeenCalledTimes(1);
  });

  it("renders disabled state", () => {
    render(<Button disabled>Désactivé</Button>);
    expect(screen.getByText("Désactivé")).toBeDisabled();
  });

  it("applies variant classes", () => {
    const { container } = render(<Button variant="danger">Supprimer</Button>);
    expect(container.firstChild).toHaveClass("bg-red-600");
  });

  it("renders with loading state", () => {
    render(<Button loading>Chargement</Button>);
    expect(screen.getByText("Chargement...")).toBeInTheDocument();
  });
});