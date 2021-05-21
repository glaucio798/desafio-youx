import Api from "./Api";

const getPacientes = () => {
  return Api().get("/pacientes");
};

const postPacientes = (dados) => {
  return Api().post("/pacientes", dados);
};

export { getPacientes, postPacientes };
