CREATE TABLE partida (
                         id BIGINT PRIMARY KEY,
                         temporada INTEGER NOT NULL,
                         rodada INTEGER NOT NULL,
                         clube_mandante_id BIGINT NOT NULL,
                         clube_visitante_id BIGINT NOT NULL,
                         gols_mandante INTEGER,
                         gols_visitante INTEGER,
                         data_hora TIMESTAMP WITH TIME ZONE,
                         valida BOOLEAN,
                         atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL,

                         CONSTRAINT ck_partida_rodada_positiva
                             CHECK (rodada > 0),

                         CONSTRAINT ck_partida_clubes_distintos
                             CHECK (clube_mandante_id <> clube_visitante_id)
);

CREATE INDEX idx_partida_temporada_rodada
    ON partida (temporada, rodada);