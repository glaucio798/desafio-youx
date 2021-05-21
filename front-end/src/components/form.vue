<template>
  <b-container>
    <b-row align-h="center">
      <div class="corpo p-4">
        <h3>Cadastro de Paciente</h3>
        <b-form @submit="submit">
          <b-form-input
            v-model="form.nome"
            placeholder="Nome"
            class="mt-4"
            required
          >
          </b-form-input>

          <input
            v-model="form.cpf"
            placeholder="CPF"
            class="form-control mt-3"
            v-mask="'###.###.###-##'"
            required
          />

          <input
            v-model="form.data"
            type="date"
            placeholder="Data de nascimento"
            class="form-control mt-3"
            required
          />

          <b-form-select
            required
            :options="options"
            v-model="form.estado"
            class="mt-3"
          >
          </b-form-select>

          <b-row no-gutters class="pesos">
            <input
              v-model="form.peso"
              placeholder="Peso (Ex. 88.88Kg)"
              class="form-control mt-3"
              v-mask="'##.#Kg'"
            />

            <input
              v-model="form.altura"
              placeholder="Altura (Ex. 1,60m)"
              class="form-control mt-3"
              v-mask="'#.##m'"
            />
          </b-row>
          <b-button type="submit" class="botao mt-3">ENVIAR</b-button>
        </b-form>
      </div>
    </b-row>
  </b-container>
</template>

<script>
import axios from "axios";
import { mask } from "vue-the-mask";
import { postPacientes } from "@/services/api/paciente";

export default {
  name: "formCadastro",
  directives: { mask },
  data() {
    return {
      options: [{ value: null, text: "Selecione um estado" }],
      form: {
        nome: "",
        cpf: "",
        peso: "",
        altura: "",
        data: "",
        estado: null,
      },
    };
  },
  methods: {
    alert(message, title, variant) {
      this.$bvToast.toast(message, {
        title,
        variant,
        autoHideDelay: 5000,
      });
    },
    submit(evt) {
      evt.preventDefault();

      postPacientes(this.form)
        .then(() => {
          this.form = {
            nome: "",
            cpf: "",
            peso: "",
            altura: "",
            data: "",
            estado: null,
          };
          this.alert("Paciente criado com sucesso", "Sucesso!!", "success");
        })
        .catch((err) => {
          this.alert(
            err.response.data.message,
            "Erro ao cadastrar usuario :(",
            "danger"
          );
        });
    },
  },
  mounted() {
    axios
      .get("https://servicodados.ibge.gov.br/api/v1/localidades/estados")
      .then((res) => {
        this.options = this.options.concat(res.data).map((item) => {
          let retorno;
          if (item.id) retorno = { value: item.sigla, text: item.nome };
          else retorno = { value: item.value, text: item.text, disabled: true };

          return retorno;
        });
      });
  },
};
</script>

<style lang="scss" scoped>
.corpo {
  margin: 0 10px;
  border-radius: 30px;
  background-color: #2d3142;
  h3 {
    color: white;
    font-weight: bold;
    padding-bottom: 10px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.4);
  }
  -webkit-box-shadow: 0px 10px 5px 0px rgba(0, 0, 0, 0.4);
  -moz-box-shadow: 0px 10px 5px 0px rgba(0, 0, 0, 0.4);
  box-shadow: 0px 10px 5px 0px rgba(0, 0, 0, 0.4);

  .botao {
    background-color: #368f8b;
    color: white;
    font-weight: bold;
    border-radius: 25px;
    border: none;
    padding: 10px 20px;
    transition: 0.5s;

    &:hover {
      filter: brightness(0.75);
    }
  }
}
</style>
