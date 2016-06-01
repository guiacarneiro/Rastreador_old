package com.project.tigunibh2015_2.rastreador.ws;

import com.project.tigunibh2015_2.rastreador.Modelos.RespostaUsuario;
import com.project.tigunibh2015_2.rastreador.Modelos.Usuario;
import com.project.tigunibh2015_2.rastreador.parametros.ParametroRastreamento;
import com.project.tigunibh2015_2.rastreador.respostas.RespostaListaUsuario;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jonatas on 31/05/2016.
 */
public class AdicionaDependenteWs {

    public RespostaListaUsuario adicionaDependente(ParametroRastreamento parametroRastreamento) throws Exception {
        final String url = "http://rastreador-unibh.rhcloud.com/RastreadorService/rest/usuario/pesquisaUsuario";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        RespostaListaUsuario result = restTemplate.postForObject( url, parametroRastreamento.getUsuarioFilho(), RespostaListaUsuario.class);

        return null;
    }
}
