 <template>
  <b-row cols="1" cols-md="2" no-gutters align-h="center">
    <b-col>
      <div class="mapa shadow ml-auto">
        <l-map :zoom="zoom" :center="center">
          <l-control-layers />
          <l-wms-tile-layer
            v-for="layer in layers"
            :key="layer.name"
            :base-url="baseUrl"
            :layers="layer.layers"
            :visible="layer.visible"
            :name="layer.name"
            layer-type="base"
          />

          <l-marker
            v-for="(estado, index) in estados"
            :key="index"
            :lat-lng="getCord(estado.cord.x, estado.cord.y)"
          >
            <l-tooltip :options="{ permanent: true, interactive: true }">
              <div>{{ estado.cadastrados }}</div>
            </l-tooltip>
          </l-marker>
        </l-map>
      </div>
    </b-col>
    <b-col>
      <div class="dados ml-0 ml-md-2 mr-auto text-left">
        <h3>Dados</h3>
        <div class="table">
          <b-table
            id="my-table"
            :items="estados"
            :fields="[
              { key: 'nome', label: 'Estado' },
              { key: 'cadastrados', label: 'Pacientes' },
            ]"
            :per-page="16"
            :current-page="page"
            small
          ></b-table>
        </div>
        <b-pagination
          v-model="page"
          per-page="16"
          hide-goto-end-buttons
          :total-rows="27"
        ></b-pagination>
      </div>
    </b-col>
  </b-row>
</template>

<script>
import "leaflet/dist/leaflet.css";
import { latLng, Icon } from "leaflet";
import {
  LMap,
  LWMSTileLayer,
  LControlLayers,
  LMarker,
  LTooltip,
} from "vue2-leaflet";
import { getPacientes } from "@/services/api/paciente";

export default {
  name: "Mapa",
  components: {
    LMap,
    "l-wms-tile-layer": LWMSTileLayer,
    LControlLayers,
    LMarker,
    LTooltip,
  },
  mounted() {
    delete Icon.Default.prototype._getIconUrl;
    Icon.Default.mergeOptions({
      iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
      iconUrl: require("leaflet/dist/images/marker-icon.png"),
      shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
    });

    getPacientes()
      .then((res) => {
        this.dados = res.data;
        this.estados.map((estado) => {
          estado.cadastrados = this.dados.filter(
            (pessoa) => pessoa.estado == estado.nome
          ).length;
        });
      })
      .catch((err) => {
        console.error(err);
      });
  },
  data() {
    return {
      page: 1,
      zoom: 4,
      center: latLng(-13.551631, -53.380419),
      baseUrl: "http://sistemas.gt4w.com.br/geoserver/processo_seletivo/wms",
      layers: [
        {
          name: "Estados brasileiros",
          visible: true,
          format: "image/png",
          layers: "processo_seletivo:ufs_brasil",
        },
      ],
      dados: [],
      estados: [
        { nome: "AC", cord: { x: -8.77, y: -70.55 }, cadastrados: 0 },
        { nome: "AL", cord: { x: -9.71, y: -35.73 }, cadastrados: 0 },
        { nome: "AM", cord: { x: -3.07, y: -61.66 }, cadastrados: 0 },
        { nome: "AP", cord: { x: 1.41, y: -51.77 }, cadastrados: 0 },
        { nome: "BA", cord: { x: -12.96, y: -38.51 }, cadastrados: 0 },
        { nome: "CE", cord: { x: -3.71, y: -38.54 }, cadastrados: 0 },
        { nome: "DF", cord: { x: -15.83, y: -47.86 }, cadastrados: 0 },
        { nome: "ES", cord: { x: -19.19, y: -40.34 }, cadastrados: 0 },
        { nome: "GO", cord: { x: -16.64, y: -49.31 }, cadastrados: 0 },
        { nome: "MA", cord: { x: -2.55, y: -44.3 }, cadastrados: 0 },
        { nome: "MT", cord: { x: -12.64, y: -55.42 }, cadastrados: 10 },
        { nome: "MS", cord: { x: -20.51, y: -54.54 }, cadastrados: 0 },
        { nome: "MG", cord: { x: -18.1, y: -44.38 }, cadastrados: 0 },
        { nome: "PA", cord: { x: -5.53, y: -52.29 }, cadastrados: 0 },
        { nome: "PB", cord: { x: -7.06, y: -35.55 }, cadastrados: 0 },
        { nome: "PR", cord: { x: -24.89, y: -51.55 }, cadastrados: 0 },
        { nome: "PE", cord: { x: -8.28, y: -35.07 }, cadastrados: 0 },
        { nome: "PI", cord: { x: -8.28, y: -43.68 }, cadastrados: 0 },
        { nome: "RJ", cord: { x: -22.84, y: -43.15 }, cadastrados: 0 },
        { nome: "RN", cord: { x: -5.22, y: -36.52 }, cadastrados: 0 },
        { nome: "RO", cord: { x: -11.22, y: -62.8 }, cadastrados: 0 },
        { nome: "RS", cord: { x: -30.01, y: -51.22 }, cadastrados: 0 },
        { nome: "RR", cord: { x: 1.89, y: -61.22 }, cadastrados: 0 },
        { nome: "SC", cord: { x: -27.33, y: -49.44 }, cadastrados: 0 },
        { nome: "SE", cord: { x: -10.9, y: -37.07 }, cadastrados: 0 },
        { nome: "SP", cord: { x: -23.55, y: -46.64 }, cadastrados: 0 },
        { nome: "TO", cord: { x: -10.25, y: -48.25 }, cadastrados: 0 },
      ],
    };
  },
  methods: {
    getCord(x, y) {
      return latLng(x, y);
    },
  },
};
</script>

<style lang="scss" scoped>
.mapa {
  border: 1px solid #c2bfc5;
  border-radius: 5px;
  overflow: hidden;
  height: 500px;
  max-width: 500px;
  // width: 100%;
}

.dados {
  .table {
    width: 200px;
    th,
    td {
      padding: 0 8px;
    }
  }
}
</style>

<style lang="scss">
.dados {
  .table {
    th,
    td {
      padding: 0 8px;
      border: none;
    }
  }
}
</style>