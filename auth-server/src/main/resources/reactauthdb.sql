--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0 (Debian 17.0-1.pgdg120+1)
-- Dumped by pg_dump version 17.0 (Debian 17.0-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: passwords; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passwords (
    passwordsid integer NOT NULL,
    password character varying(30) NOT NULL,
    creationdate date NOT NULL,
    updatedate date,
    usersid integer NOT NULL
);


ALTER TABLE public.passwords OWNER TO postgres;

--
-- Name: passwords_passwordsid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passwords_passwordsid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.passwords_passwordsid_seq OWNER TO postgres;

--
-- Name: passwords_passwordsid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.passwords_passwordsid_seq OWNED BY public.passwords.passwordsid;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    rolesid bigint NOT NULL,
    rolesname character varying(255) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    usersid integer NOT NULL,
    identificationid bigint NOT NULL,
    fullname character varying(20) NOT NULL,
    lastname character varying(20) NOT NULL,
    email character varying(20) NOT NULL,
    creationdate date,
    updatedate date
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: users_usersid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_usersid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.users_usersid_seq OWNER TO postgres;

--
-- Name: users_usersid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_usersid_seq OWNED BY public.users.usersid;


--
-- Name: usuarios_roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuarios_roles (
    usuariosrolesid bigint NOT NULL,
    rolesid bigint NOT NULL,
    usersid bigint NOT NULL
);


ALTER TABLE public.usuarios_roles OWNER TO postgres;

--
-- Name: passwords passwordsid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwords ALTER COLUMN passwordsid SET DEFAULT nextval('public.passwords_passwordsid_seq'::regclass);


--
-- Name: users usersid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN usersid SET DEFAULT nextval('public.users_usersid_seq'::regclass);


--
-- Data for Name: passwords; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.passwords (passwordsid, password, creationdate, updatedate, usersid) FROM stdin;
5	securepassword123	2024-10-18	\N	12
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.roles (rolesid, rolesname) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (usersid, identificationid, fullname, lastname, email, creationdate, updatedate) FROM stdin;
1	12345672	John	Doe	john.doe@example.com	2023-01-01	2023-01-01
4	123456781	John	Doe	john.doe@example.com	2023-01-01	2023-01-01
5	123456782	John	Doe	john.doe@example.com	2023-01-01	2023-01-01
7	123456783	John	Doe	john.doe@example.com	2024-10-18	\N
8	123456784	John	Doe	john.doe@example.com	2024-10-18	\N
9	123456785	John	Doe	john.doe@example.com	2024-10-18	\N
10	6	John	Doe	john.doe@example.com	2024-10-18	\N
11	123456787	John	Doe	john.doe@example.com	2024-10-18	\N
12	123456788	John	Doe	john.doe@example.com	2024-10-18	\N
\.


--
-- Data for Name: usuarios_roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuarios_roles (usuariosrolesid, rolesid, usersid) FROM stdin;
\.


--
-- Name: passwords_passwordsid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.passwords_passwordsid_seq', 5, true);


--
-- Name: users_usersid_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_usersid_seq', 12, true);


--
-- Name: passwords passwords_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwords
    ADD CONSTRAINT passwords_pkey PRIMARY KEY (passwordsid);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (rolesid);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (usersid);


--
-- Name: usuarios_roles usuarios_roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT usuarios_roles_pkey PRIMARY KEY (usuariosrolesid);


--
-- Name: usuarios_roles rolesid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT rolesid FOREIGN KEY (rolesid) REFERENCES public.roles(rolesid) NOT VALID;


--
-- Name: passwords users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passwords
    ADD CONSTRAINT users FOREIGN KEY (usersid) REFERENCES public.users(usersid) MATCH FULL NOT VALID;


--
-- Name: usuarios_roles usersid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuarios_roles
    ADD CONSTRAINT usersid FOREIGN KEY (rolesid) REFERENCES public.users(usersid) NOT VALID;


--
-- PostgreSQL database dump complete
--

