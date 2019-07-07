select descripcion, facturas.importe, count(*), sum(importe) from facturas, episodios, actividades where episodios.id = facturas.episodio and actividades.id = episodios.tipoepisodio group by actividades.descripcion, facturas.importe 